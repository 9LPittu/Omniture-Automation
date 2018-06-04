const chrome = require('selenium-webdriver/chrome')
let JestScreenshotReporter = require('jest-screenshot-reporter');
jasmine.getEnv().addReporter(new JestScreenshotReporter({ browser }));
