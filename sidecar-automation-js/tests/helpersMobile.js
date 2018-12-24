//import { Builder } from 'selenium-webdriver';

const { Builder, Capabilities, By, Key, until } = require('selenium-webdriver')
const {chrome, Options} = require('selenium-webdriver/chrome')
const firefox = require('selenium-webdriver/firefox')
const each = require('jest-each')
/*
var capabilities = {
  browserName: 'firefox',
  chromeOptions: {
    mobileEmulation: {
      deviceName: 'iPhone 7'   //Google Nexus 5
    }
  }
};
*/
// https://seleniumhq.github.io/selenium/docs/api/javascript/module/selenium-webdriver/index_exports_Capabilities.html

export const driver = new Builder()
  .forBrowser('chrome')
//  .withCapabilities(capabilities)
 // .usingServer('http://jui-ci-p02.jcrew.com:4444/wd/hub')
.setChromeOptions(
           new Options().setMobileEmulation({deviceName: 'iPhone 7'}))
  .usingServer('http://localhost:4444/wd/hub')
  .build();

//mobile mode
driver.manage().window().setSize(375,667)

afterAll(async () => {
  // Cleanup `process.on('exit')` event handlers to prevent a memory leak caused by the combination of `jest` & `tmp`.
  for (const listener of process.listeners('exit')) {
    listener();
    process.removeListener('exit', listener);
  }
  await driver.quit();
});

export const defaultTimeout = 50e3;
