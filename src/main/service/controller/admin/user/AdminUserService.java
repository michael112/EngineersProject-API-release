package main.service.controller.admin.user;

import main.json.admin.user.AccountJson;

import main.json.admin.user.view.AccountListJson;
import main.json.admin.user.view.AccountInfoJson;

import main.json.admin.user.field.UsernameJson;
import main.json.admin.user.field.NameJson;
import main.json.admin.user.field.EmailJson;
import main.json.user.PhoneJson;

import main.model.user.User;
import main.model.user.userprofile.Address;

public interface AdminUserService {

    AccountListJson getAccountList();

    void addAccount(AccountJson newAccount);

    AccountInfoJson getAccountInfo(User user);

    UsernameJson getUsernameInfo(User user);

    NameJson getNameInfo(User user);

    EmailJson getEmailInfo(User user);

    PhoneJson getPhoneInfo(User user, String phoneID);

    Address getAddressInfo(User user);

    void editAccount(User user, AccountJson editedAccount);

    void deactivateAccount(User user);

    void activateAccount(User user);

    void resetUserPassword(User user);

}
