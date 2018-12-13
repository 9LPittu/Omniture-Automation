package com.jcrew.cucumber.container;

import com.jcrew.helper.BrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class DomContainer {
	
	int i = 0;
    @FindBy(how = How.ID, using = "j_username")
    public WebElement username;

    @FindBy(how = How.ID, using = "j_password")
    public WebElement password;

    @FindBy(how = How.NAME, using = "btnEnter")
    public WebElement login;

    @FindBy(how = How.ID, using = "phMenu")
    public WebElement menu;

    @FindBy(how = How.XPATH, using = "//a[text()='Order Lifecycle']")
    public WebElement orderLifeCycle;

    @FindBy(how = How.XPATH, using = "//a[text()='Sales Orders']")
    public WebElement salesOrders;

    @FindBy(how = How.XPATH, using = "//*[@id='MIDP205']/a")
    public WebElement dcOrdersMenu;

    //@FindBy(how = How.XPATH, using = "//div[@class='fltr_rightBdr fltr_capShow'][1]//input[7]")
    @FindBy(how = How.ID, using = "dataForm:SOList_entityListView:SOList_filterId2:field6value1")
    public WebElement orderNumber;

    @FindBy(how = How.ID, using = "dataForm:DOList_entityListView:DistributionOrderlist1:field5value1")
    public WebElement dcOrderId;

    @FindBy(how = How.ID, using = "dataForm:DOList_entityListView:DistributionOrderlist1:DistributionOrderlist1apply")
    public WebElement dcOrderIdApply;

    @FindBy(how = How.CLASS_NAME, using = "fltrHidden")
    public WebElement showFilterOptions;

    @FindBy(how = How.XPATH, using = "//div[@id='ListView_SOList_entityListView']/div/table/tbody/tr[1]/td[3]//table[1]//div[@class='fltr_rightBdr']//table")
    public WebElement selectFilterOptions;

    @FindBy(how = How.XPATH, using = "//body/div[4]//tr[1]//ol[1]/li[1]")
    public WebElement selectFilterOptionsAll;

    @FindBy(how = How.XPATH, using = "//body/div[4]//tr[2]//button[1]")
    public WebElement selectFilterButtonOk;

    @FindBy(how = How.XPATH, using = "//div[@id='ListView_SOList_entityListView']/div/table//tr[1]/td[4]/div[1]/span/table")
    public WebElement applyFilter;

    @FindBy(how = How.ID, using = "dataForm:SOList_entityListView:salesOrderData_bodyDiv")
    public List<WebElement> searchResults;
//*[@id='dataForm:SOList_entityListView:salesOrderData:0:statusDesc1']
    @FindBy(how = How.XPATH, using = "//*[@id='dataForm:SOList_entityListView:salesOrderData_bodyDiv']//table/tbody/tr/td[5]/div/span")
    public List<WebElement> orderStatus;

    @FindBy(how = How.ID, using = "dataForm:drop_down_page_ids_som")
    public WebElement selectOptions;

    @FindBy(how = How.ID, using = "customerOrderLineAdditionalDetailTab_lnk")
    public WebElement lineAdditionalDetails;

    @FindBy(how = How.XPATH, using = "//*[@id='dataForm:coLineViewAdditionalListTable_body']/tbody/tr/td[1][contains(@class,'tbl_checkBox advtbl_col advtbl_body_col')]")
    public List<WebElement> lineIds;

    @FindBy(how = How.ID, using = "dataForm:coLViewAI_createDOButton")
    public WebElement createDo;

    @FindBy(how = How.ID, using = "dataForm:dolinelistview_id:DOLineList_MainListTable:0:DO_Lines_List_DOId_param_Out")
    public WebElement doNum;

    @FindBy(how = How.XPATH, using = "//*[@id='dataForm:DOList_entityListView:DOList_MainListTable_body']/tbody/tr[1]/td[7]/div")
    public WebElement doOderStatus;

    @FindBy(how = How.ID, using = "dataForm:DODetailsMain_DORelease_button")
    public WebElement doReleaseBtn;

    @FindBy(how = How.ID, using = "dataForm:header_1")
    public WebElement orderPageHeader;

    @FindBy(how = How.ID, using = "dataForm:op_txt_postorder_view_createCO_status")
    public WebElement doStatus;

    @FindBy(how = How.ID, using = "dataForm:DODetailsMainHeader_Out_FulfillmentStatus")
    public WebElement doFulfillStatus;

    public WebElement doStatusText(String doStatus) {
        return BrowserDriver.getCurrentDriver().findElement(By.xpath("//table[@id='dataForm:postorder_view_createCO_header_GL']//span[text()='"+doStatus+"']"));
    }

    @FindBy(how = How.XPATH, using = "//table[@id='dataForm:dolinelistview_id:DOLineList_MainListTable_body']/tbody/tr[@class='advtbl_row']")
    public List<WebElement> doDetails;

    @FindBy(how = How.XPATH, using = "//*[@id='pghdr']/tbody/tr[1]/td[1]/table/tbody/tr/td[10]/a")
    public WebElement logout;

    @FindBy(how = How.ID, using = "SignoutOK")
    public WebElement logoutOk;

    @FindBy(how = How.CSS, using = "input[id='dataForm:filter_order:field2value1']")
    public WebElement blueMartiniDoId;

    @FindBy(how = How.ID, using = "dataForm:filter_order:field1value1")
    public WebElement orderIdInStoreLogin;

    @FindBy(how = How.XPATH, using = "//div[@id='dataForm:filter_order:filter_order_quickFilterGroupButton_mainButton']")
    public WebElement doPageApply;

    public List<WebElement> doStoreCheckbox(){
        return BrowserDriver.getCurrentDriver().findElements(By.xpath("//table[@id='dataForm:detailsTable_body']//tr[@class='advtbl_row']/td[1]/input[1]"));
    }

    public List<WebElement> doStoreStatus(){
        return BrowserDriver.getCurrentDriver().findElements(By.xpath("//*[@id='dataForm:OrderListPage_entityListView:releaseDataTable_body']/tbody/tr/td[5]/span[1]"));
    }

    @FindBy(how = How.CSS, using = "input[id='dataForm:printPickListBtn']")
    public WebElement acceptAndPickListBtn;

    @FindBy(how = How.CSS, using = "input[id='dataForm:packItemsBtn']")
    public WebElement packListBtn;

    @FindBy(how = How.XPATH, using = ".//*[@id='dataForm:OrderListPage_entityListView:releaseDataTable']/thead/tr/td[1]/input")
    public WebElement allCheckBox;

    @FindBy(how = How.XPATH, using = "//a[@id='dataForm:coROILTable:0:coROILCOLineItemIdLink']")
    public WebElement itemId;

    @FindBy(how = How.XPATH, using = "//*[@id='dataForm:inputTextUPC1']")
    public WebElement upc;
  
    @FindBy(how = How.XPATH, using = "//*[@id='dataForm:ScanPackScanBtn']")
    public WebElement enterUpc;
    
    @FindBy(how = How.XPATH, using = "//input[@id='dataForm:ScanPack_LPN_LPNWeight_Mask']")
    public WebElement actualWeight;

    @FindBy(how = How.XPATH, using = "//*[@id='dataForm:SaveAndExitBtn']")
    public WebElement finishPacking;

    @FindBy(how = How.ID, using = "dataForm:d_ShipLPN_cust")
    public WebElement shipItem;

    @FindBy(how = How.XPATH, using = "//*[@id='container']/table[2]/tbody/tr[2]/td/input")
    public WebElement devToolsPwd;

    @FindBy(how = How.XPATH, using = "//*[@id='container']/table[3]/tbody/tr/td/a/img")
    public WebElement devToolsSubmit;
    
    @FindBy(how = How.XPATH, using = "//button[text()='Continue']")
    public WebElement devToolContinue;
  
    @FindBy(how = How.XPATH, using = "//*[@id='maincon']/form/table/tbody/tr/td[2]/input")
    public WebElement devToolsShipmentId;

    @FindBy(how = How.XPATH, using = "//*[@id='maincon']/form/table/tbody/tr/td[3]/input")
    public WebElement devToolsUpdateShipmentId;
    @FindBy(how = How.XPATH, using = "//div/table/tbody/tr/td[@class='textsmhead']")
    public WebElement shipmentUpdated;
    @FindBy(how = How.XPATH, using = "//*[@id='dataForm:SOList_entityListView:salesOrderData:0:domordTyp']")
    public WebElement orderType;
    @FindBy(how = How.XPATH, using = "//span[@id='dataForm:scanWorkspaceDataTable:headDescription']")
    public WebElement itemDesc;
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Same UPC/Item exists in Mutliple Order Lines')]")
    public WebElement selectItemPopUp;
    @FindBy(how = How.XPATH, using = "//input[@id='checkAll_c_dataForm:ScanOrderLineList_dataTable']")
    public WebElement clickRadioButton;
    @FindBy(how = How.XPATH, using = "//div[@id='multGTIN_RActionPanel_Buttons']/input[@value='Select']")
    public WebElement selectButton;
    @FindBy(how = How.XPATH, using = "//table[@id='dataForm:dolinelistview_id:DOLineList_MainListTable_body']/tbody/tr/td[9]/a")
    public List<WebElement> itemsList;
    @FindBy(how = How.XPATH, using = "//table[@id='dataForm:dolinelistview_id:DOLineList_MainListTable_body']/tbody/tr/td[9]/a/../../td[7]")
    public List<WebElement> rtlItem;
    @FindBy(how = How.XPATH, using = "//h2[contains(text(),'Tracking & Receiving')]")
    public static WebElement manualTrack;
    @FindBy(how = How.XPATH, using = "//input[@name='store']")
    public static WebElement enterStoreId;
    @FindBy(how = How.XPATH, using = "//input[@name='userid']")
    public static WebElement enterUserId;
    @FindBy(how = How.XPATH, using = "//input[@name='order']")
    public static WebElement enterOrderNum;
    @FindBy(how = How.XPATH, using = "//input[@name='Send']")
    public static WebElement sendButton;
    @FindBy(how = How.XPATH, using = "//select[@name='updTR']")
    public static WebElement trackNumList;
    @FindBy(how = How.XPATH, using = "//input[@name='UpdateTR']")
    public static WebElement submitButton;
    @FindBy(how = How.XPATH, using = "//div[@class='alert alert-success']")
    public static WebElement submitionSuccess;
    @FindBy(how = How.ID, using = "order")
    public static WebElement enterOrderNum_sign;
    @FindBy(how = How.NAME, using = "Details")
    public static WebElement submit_sign;
    @FindBy(how = How.CLASS_NAME, using = "pad")
    public static WebElement signature;
    @FindBy(how = How.NAME, using = "Done")
    public static WebElement doneSigning;
    @FindBy(how = How.XPATH, using = "//span[text()='ORDER PICKED UP']")
    public static WebElement pickedUp;
    @FindBy(how = How.XPATH, using = "//span[text()='PARTIAL ORDER PICKUP']")
    public static WebElement partialPickedUp;
    
  
  
    
    
}
