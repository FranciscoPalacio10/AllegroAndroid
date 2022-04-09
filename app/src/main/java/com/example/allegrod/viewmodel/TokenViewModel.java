package com.example.allegrod.viewmodel;

import static java.util.Calendar.HOUR;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.allegrod.constants.AppConstant;
import com.example.allegrod.models.token.TokenResponse;
import com.example.allegrod.models.user.get.UserGetResponse;
import com.example.allegrod.repository.TokenRepository;
import com.example.allegrod.repository.UserRepository;
import com.example.allegrod.response.ApiResponse;
import com.example.allegrod.services.SessionService;
import com.example.allegrod.webservices.token.OnCallBackResponse;

import java.util.Calendar;
import java.util.Date;

public class TokenViewModel extends AndroidViewModel {
    private final TokenRepository tokenRepository;
    private SessionService sessionService;

    public TokenViewModel(Application application) {
        super(application);
        this.tokenRepository = new TokenRepository(application.getApplicationContext());
        sessionService = new SessionService(application.getApplicationContext());
    }

    private void loadBearer(String email) {
        tokenRepository.getToken(email, new OnCallBackResponse<ApiResponse<TokenResponse>>() {
            @Override
            public void saveResponse(ApiResponse<TokenResponse> token) {
                if (token.isSuccesful()) {
                    sessionService.SaveBearerToken(token.getData().token);
                } else {
                    sessionService.SaveBearerToken("");
                }
            }
        });
    }

    public String getBearerToken(String email) {
        Date dateSaved = sessionService.GetBearerTokenTimeCreated();
        if (dateSaved == null) {
            loadBearer(email);
        } else {
            Date dateSavedMoreThree = addHoursToJavaUtilDate(dateSaved,3);
            if (dateSaved.after(dateSavedMoreThree)) {
                loadBearer(email);
            }
        }
        return sessionService.GetBearerToken();
    }

    public Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
