package com.jcrew.pojo;

public class Country {
	
	private String code;
	private String currency;
	
	public Country(String countryName){
		
		countryName = countryName.toUpperCase();
		
		switch(countryName){
			case "UNITED STATES":
				code = "US";
				currency = "$";
				break;
			case "CANADA":
				code = "ca";
				currency = "cad";
				break;
			case "AUSTRALIA":
				code = "au";
				currency = "aud";
				break;
			case "BANGLADESH":
				code = "bd";
				currency = "usd";
				break;
			case "BRUNEI":
				code = "bn";
				currency = "usd";
				break;
			case "CAMBODIA":
				code = "kh";
				currency = "usd";
				break;
			case "CHINA":
				code = "cn";
				currency = "cny";
				break;
			case "HONG KONG":
				code = "hk";
				currency = "hk$";
				break;
			case "INDIA":
				code = "in";
				currency = "inr";
				break;
			case "INDONESIA":
				code = "id";
				currency = "idr";
				break;
			case "JAPAN":
				code = "jp";
				currency = "jpy";
				break;
			case "MACAU":
				code = "mo";
				currency = "hkd";
				break;
			case "MALDIVES":
				code = "mv";
				currency = "usd";
				break;
			case "NEW ZEALAND":
				code = "nz";
				currency = "nzd";
				break;
			case "PAKISTAN":
				code = "pk";
				currency = "usd";
				break;
			case "PHILIPPINES":
				code = "ph";
				currency = "php";
				break;
			case "SINGAPORE":
				code = "sg";
				currency = "sgd";
				break;
			case "SOUTH KOREA":
				code = "kr";
				currency = "krw";
				break;
			case "SRI LANKA":
				code = "lk";
				currency = "usd";
				break;
			case "TAIWAN":
				code = "tw";
				currency = "twd";
				break;
			case "THAILAND":
				code = "th";
				currency = "thb";
				break;
			case "AUSTRIA":
				code = "at";
				currency = "€";
				break;
			case "BELGIUM":
				code = "be";
				currency = "€";
				break;
			case "BULGARIA":
				code = "bg";
				currency = "€";
				break;
			case "CROATIA":
				code = "hr";
				currency = "hrk";
				break;
			case "CYPRUS":
				code = "cy";
				currency = "€";
				break;
			case "CZECH REPUBLIC":
				code = "cz";
				currency = "czk";
				break;
			case "DENMARK":
				code = "dk";
				currency = "dkk";
				break;
			case "ESTONIA":
				code = "ee";
				currency = "€";
				break;
			case "FINLAND":
				code = "fi";
				currency = "€";
				break;
			case "FRANCE":
				code = "fr";
				currency = "€";
				break;
			case "GERMANY":
				code = "de";
				currency = "€";
				break;
			case "GIBRALTAR":
				code = "gi";
				currency = "£";
				break;
			case "GREECE":
				code = "gr";
				currency = "€";
				break;
			case "GUERNSEY":
				code = "gg";
				currency = "£";
				break;
			case "HUNGARY":
				code = "hu";
				currency = "huf";
				break;
			case "ICELAND":
				code = "is";
				currency = "€";
				break;
			case "IRELAND":
				code = "ie";
				currency = "€";
				break;
			case "ITALY":
				code = "it";
				currency = "€";
				break;
			case "JERSEY":
				code = "je";
				currency = "£";
				break;
			case "LATVIA":
				code = "lv";
				currency = "€";
				break;
			case "LIECHTENSTEIN":
				code = "li";
				currency = "€";
				break;
			case "LITHUANIA":
				code = "lt";
				currency = "€";
				break;
			case "LUXEMBOURG":
				code = "lu";
				currency = "€";
				break;
			case "MALTA":
				code = "mt";
				currency = "€";
				break;
			case "MONACO":
				code = "mc";
				currency = "€";
				break;
			case "NETHERLANDS":
				code = "nl";
				currency = "€";
				break;
			case "NORWAY":
				code = "no";
				currency = "nok";
				break;
			case "POLAND":
				code = "pl";
				currency = "pln";
				break;
			case "PORTUGAL":
				code = "pt";
				currency = "€";
				break;
			case "ROMANIA":
				code = "ro";
				currency = "€";
				break;
			case "RUSSIAN FEDERATION":
				code = "ru";
				currency = "€";
				break;
			case "SLOVAKIA":
				code = "sk";
				currency = "€";
				break;
			case "SLOVENIA":
				code = "si";
				currency = "€";
				break;
			case "SPAIN":
				code = "es";
				currency = "€";
				break;
			case "SWEDEN":
				code = "se";
				currency = "sek";
				break;
			case "SWITZERLAND":
				code = "ch";
				currency = "chf";
				break;
			case "UNITED KINGDOM":
				code = "uk";
				currency = "£";
				break;
			case "ANTIGUA AND BARBUDA":
				code = "ag";
				currency = "usd";
				break;
			case "ARUBA":
				code = "aw";
				currency = "usd";
				break;
			case "BARBADOS":
				code = "bb";
				currency = "bbd";
				break;
			case "BELIZE":
				code = "bz";
				currency = "usd";
				break;
			case "BERMUDA":
				code = "bm";
				currency = "usd";
				break;
			case "BOLIVIA":
				code = "bo";
				currency = "usd";
				break;
			case "BRAZIL":
				code = "br";
				currency = "usd";
				break;
			case "CAYMAN ISLANDS":
				code = "ky";
				currency = "kyd";
				break;
			case "CHILE":
				code = "cl";
				currency = "clp";
				break;
			case "COLOMBIA":
				code = "co";
				currency = "cop";
				break;
			case "COSTA RICA":
				code = "cr";
				currency = "usd";
				break;
			case "DOMINICA":
				code = "dm";
				currency = "usd";
				break;
			case "DOMINICAN REPUBLIC":
				code = "do";
				currency = "usd";
				break;
			case "ECUADOR":
				code = "ec";
				currency = "usd";
				break;
			case "EL SALVADOR":
				code = "sv";
				currency = "usd";
				break;
			case "FRENCH GUIANA":
				code = "gf";
				currency = "€";
				break;
			case "GRENADA":
				code = "gd";
				currency = "usd";
				break;
			case "GUADELOUPE":
				code = "gp";
				currency = "€";
				break;
			case "GUATEMALA":
				code = "gt";
				currency = "usd";
				break;
			case "HONDURAS":
				code = "hn";
				currency = "usd";
				break;
			case "JAMAICA":
				code = "jm";
				currency = "usd";
				break;
			case "MARTINIQUE":
				code = "mq";
				currency = "€";
				break;
			case "MEXICO":
				code = "mx";
				currency = "mxn";
				break;
			case "MONTSERRAT":
				code = "ms";
				currency = "usd";
				break;
			case "NICARAGUA":
				code = "ni";
				currency = "usd";
				break;
			case "PANAMA":
				code = "pa";
				currency = "usd";
				break;
			case "PARAGUAY":
				code = "py";
				currency = "usd";
				break;
			case "PERU":
				code = "pe";
				currency = "pen";
				break;
			case "SAINT KITTS AND NEVIS":
				code = "kn";
				currency = "usd";
				break;
			case "SAINT LUCIA":
				code = "lc";
				currency = "usd";
				break;
			case "TRINIDAD AND TOBAGO":
				code = "tt";
				currency = "usd";
				break;
			case "TURKS AND CAICOS ISLANDS":
				code = "tc";
				currency = "usd";
				break;
			case "BAHRAIN":
				code = "bh";
				currency = "bhd";
				break;
			case "EGYPT":
				code = "eg";
				currency = "egp";
				break;
			case "ISRAEL":
				code = "il";
				currency = "ils";
				break;
			case "JORDAN":
				code = "jo";
				currency = "jod";
				break;
			case "KUWAIT":
				code = "kw";
				currency = "kwd";
				break;
			case "OMAN":
				code = "om";
				currency = "omr";
				break;
			case "QATAR":
				code = "qa";
				currency = "qar";
				break;
			case "REUNION":
				code = "re";
				currency = "red";
				break;
			case "SAUDI ARABIA":
				code = "sa";
				currency = "sar";
				break;
			case "TURKEY":
				code = "tr";
				currency = "try";
				break;
			case "UNITED ARAB EMIRATES":
				code = "ae";
				currency = "aed";
				break;
		}
	}
	
	public String getCountryCode(){
		return code;
	}
	
	public String getCountryCurrency(){
		return currency;
	}
}