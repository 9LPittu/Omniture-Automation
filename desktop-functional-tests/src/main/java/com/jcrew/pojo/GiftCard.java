package com.jcrew.pojo;

import java.util.Date;

public class GiftCard {
	
	private String giftCardType;
	private String giftCardAmount;
	private String senderName;
	private String recipientName;
	private String recipientEmailAddress;
	private Date dateToBeSent;
	private String line1;
	private String line2;
	private String giftMessage;
	
	public GiftCard(String giftCardType, String giftCardAmount, String senderName, String recipientName, String recipientEmailAddress, String line1, String line2){
		this.giftCardType = giftCardType;
		this.giftCardAmount = giftCardAmount;
		this.senderName = senderName;
		this.recipientName = recipientName;
		this.recipientEmailAddress = recipientEmailAddress;
		this.line1 = line1;
		this.line2 = line2;
	}
	
	public GiftCard(String giftCardType, String giftCardAmount, String senderName, String recipientName, String recipientEmailAddress, Date dateToBeSent, String giftMessage){
		this.giftCardType = giftCardType;
		this.giftCardAmount = giftCardAmount;
		this.senderName = senderName;
		this.recipientName = recipientName;
		this.recipientEmailAddress = recipientEmailAddress;
		this.dateToBeSent = dateToBeSent;
		this.giftMessage = giftMessage;
	}
	
	public String getGiftCardType(){
		return giftCardType;
	}
	
	public String getGiftCardAmount(){
		return giftCardAmount;
	}
	
	public String getSenderName(){
		return senderName;
	}
	
	public String getRecipientName(){
		return recipientName;
	}
	
	public String getRecipientEmailAddress(){
		return recipientEmailAddress;
	}
	
	public Date getDateToBeSent(){
		return dateToBeSent;
	}
	
	public String getLine1(){
		return line1;
	}
	
	public String getLine2(){
		return line2;
	}
	
	public String getGiftMessage(){
		return giftMessage;
	}
}