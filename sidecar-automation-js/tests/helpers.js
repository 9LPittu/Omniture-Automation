//import { Builder } from 'selenium-webdriver';

const { Builder, By, Key, until } = require('selenium-webdriver')
const chrome = require('selenium-webdriver/chrome')
const each = require('jest-each')

var capabilities = {
  browserName: 'chrome',
  chromeOptions: {
    args :
      [
                  'headless',
                  'disable-gpu'
              ]
    }
  }


//new chrome.Options().headless().addArguments("--disable-gpu", "--no-sandbox")
export const driver = new Builder()
  .forBrowser('chrome')
//  .withCapabilities(capabilities)
// .usingServer('http://10.20.11.24:4444/wd/hub')
  .usingServer('http://localhost:4444/wd/hub')
  .build();

//mobile mode
//  driver.manage().window().setSize(360,640)

afterAll(async () => {
  // Cleanup `process.on('exit')` event handlers to prevent a memory leak caused by the combination of `jest` & `tmp`.
  for (const listener of process.listeners('exit')) {
    listener();
    process.removeListener('exit', listener);
  }
  setTimeout(() => process.exit(), 1000)
  await driver.quit();
  });

export const defaultTimeout = 50e3;
