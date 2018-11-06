package com.jcrew.helper.mailclient;

import javax.mail.Message;

public interface EmailClient {
    String getEmailRecipients();

    String getEmailSubject();

    void closeEmail();

    String getEmailBody();

    Message getEmailFromInboxWithPreheader(String username, String password, String preHeader);

    Message getEmailFromInboxWithSubject(String username, String password, String preHeader);
}
