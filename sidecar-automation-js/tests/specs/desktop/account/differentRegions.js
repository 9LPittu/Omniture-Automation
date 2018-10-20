import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';


const { Builder, By, Key, until } = require('selenium-webdriver')

 beforeAll(async () => {
   await load();
   await driver.sleep(2000)
    expect(await driver.getTitle()).toMatch('J.Crew')
  })

  test('Verify Context Chooser functionality ', async () => {
      await driver.executeScript('window.scrollTo(0, 10000)')
      await driver.sleep(1000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@class='footer__country-context__link']")));
      await driver.sleep(1000)
      await driver.findElement(By.xpath("//a[@class='footer__country-context__link']")).click()
      await driver.sleep(2000)
	  await closeIconInPAP()
      await driver.findElement(By.xpath("//*[@id='page__international']/article/section[1]/div/div[2]/div/div[1]/ul/li[2]/button")).click()
      let url = await driver.getCurrentUrl();
      if (url.indexOf("ca") > -1) {
        console.log('user is on Canada context')
      }

})


afterAll(async () => {
  await driver.quit()
})
