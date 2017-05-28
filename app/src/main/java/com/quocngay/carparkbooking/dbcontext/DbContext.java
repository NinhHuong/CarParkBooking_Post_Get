package com.quocngay.carparkbooking.dbcontext;

import com.quocngay.carparkbooking.model.BookedTicketModel;
import com.quocngay.carparkbooking.model.GaraModel;
import com.quocngay.carparkbooking.other.Constant;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ninhh on 5/24/2017.
 */

public class DbContext {
    public Realm realm;

    public DbContext() {
        realm = Realm.getDefaultInstance();
    }

    private static DbContext inst;

    public static DbContext getInst() {
        if (inst == null) {
            return new DbContext();
        }
        return inst;
    }

    //region GaraModel
    public void addGaraModel(GaraModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public int getMaxGaraModelId() {
        try{
            return realm.where(GaraModel.class).max("id").intValue();
        } catch(Exception ex){}
        return 0;
    }

    public List<GaraModel> getAllGaraModel(){
        return realm.where(GaraModel.class).findAll();
    }

    public GaraModel getGaraModelByID(int id) {
        return realm.where(GaraModel.class).equalTo("id", id).findFirst();
    }

    public void removeSingleGaraModel(int id) {
        final GaraModel result = realm.where(GaraModel.class).equalTo("id", id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteFromRealm();
            }
        });
    }

    public void deleteAllGaraModel() {
        final RealmResults<GaraModel> result = realm.where(GaraModel.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteAllFromRealm();
            }
        });
    }
    //endregion

    //region BookedTicketModel
    public void addBookedTicketModel(BookedTicketModel model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public int getMaxBookedTicketModelId() {
        try{
            return realm.where(BookedTicketModel.class).max("id").intValue();
        } catch(Exception ex){}
        return 0;
    }

    public List<BookedTicketModel> getAllBookedTicketModel(){
        return realm.where(BookedTicketModel.class).findAll();
    }

    public List<BookedTicketModel> getAllOpenBookedTicketModel(){
        Date date = new Date(new Date().getTime() - Constant.KEY_EXPIRED_TICKET);
        return realm.where(BookedTicketModel.class)
                .beginGroup().isNotNull("checkinTime").isNull("checkoutTime").endGroup()
                .or()
                .beginGroup().isNull("checkinTime").greaterThan("bookedTime", date).endGroup()
//                .equalTo("isExpired", false)
                .findAll();
    }

    public BookedTicketModel getBookedTicketModelByID(int id) {
        return realm.where(BookedTicketModel.class).equalTo("id", id).findFirst();
    }

    public void removeSingleBookedTicketModel(int id) {
        final BookedTicketModel result = realm.where(BookedTicketModel.class).equalTo("id", id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteFromRealm();
            }
        });
    }

    public void deleteAllBookedTicketModel() {
        final RealmResults<BookedTicketModel> result = realm.where(BookedTicketModel.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteAllFromRealm();
            }
        });
    }
    //endregion
}
