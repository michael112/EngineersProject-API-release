package main.json.admin.user.view;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AccountListJson {

    @Getter
    private Set<AccountInfoJson> users;

    public void addAccount(AccountInfoJson account) {
        this.users.add(account);
    }

    public AccountListJson() {
        this.users = new HashSet<>();
    }

}
