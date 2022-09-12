package com.example.allegrobackend.services.user;

import android.content.Context;

import androidx.room.Room;

import com.example.allegrobackend.localdb.AllegroLocalDb;
import com.example.allegrobackend.localdb.UserDao;
import com.example.allegrobackend.models.InitActitvy;
import com.example.allegrobackend.models.user.UserRequest;
import com.example.allegrobackend.models.user.get.UserResponse;
import com.example.allegrobackend.models.response.ApiResponse;
import com.example.allegrobackend.services.DateService;
import com.example.allegrobackend.webservices.token.OnCallBackResponse;

import java.util.Date;
import java.util.concurrent.Executors;

public class UserRoomService implements IUser {
    AllegroLocalDb db;
    UserDao userDao;

    public UserRoomService(Context context) {
        db = Room.databaseBuilder(context,
                AllegroLocalDb.class, "database-allegro").
                fallbackToDestructiveMigration().
                build();
        userDao = db.userDao();
    }

    @Override
    public void get(String id, OnCallBackResponse<UserResponse> callback, InitActitvy initActitvy) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                UserResponse user = userDao.findByEmail(id);
                if (user != null && isLastRequestBefore24hsAgo(user.dateUpdate)) {
                    callback.saveResponse(new ApiResponse<UserResponse>(user));
                } else {
                    callback.saveResponse(new ApiResponse<UserResponse>("not found", 400));
                }
            }
        });
    }

    private boolean isLastRequestBefore24hsAgo(Date userLastUpdate) {
        return userLastUpdate != null && userLastUpdate.before(DateService.getInstance().
                addHoursToJavaUtilDate(userLastUpdate, 24));
    }

    @Override
    public void add(UserRequest entity, OnCallBackResponse<UserResponse> callback, InitActitvy initActitvy) {

    }

    @Override
    public void update(UserRequest entity, String id, OnCallBackResponse<UserResponse> callbac, InitActitvy initActitvy) {

    }

    @Override
    public void add(UserResponse entity, InitActitvy initActitvy) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                int idUserIfExist = getIdUser(entity.user.email);
                //si existe el usuario lo actualizamos por el id.
                if (idUserIfExist > 0) {
                    entity.key = idUserIfExist;
                }
                entity.dateUpdate = DateService.getInstance().getDateNow();
                userDao.insertAll(entity);
            }
        });
    }

    public int getIdUser(String email) {
        UserResponse userResponse = userDao.findByEmail(email);
        if (userResponse != null) {
            return userResponse.key;
        }
        return 0;
    }
}
