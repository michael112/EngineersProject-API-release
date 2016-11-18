package main.service.controller.admin.user;

import main.json.admin.user.AccountJson;

import main.json.admin.user.view.AccountListJson;
import main.json.admin.user.view.AccountInfoJson;

import main.model.user.User;

public interface AdminUserService {

    AccountListJson getAccountList();

    void addAccount(AccountJson newAccount);

    AccountInfoJson getAccountInfo(User user);

    void editAccount(User user, AccountJson editedAccount);

    void deactivateAccount(User user);

    void activateAccount(User user);

    void resetUserPassword(User user);

}
