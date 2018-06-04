
# Running Specs

Let's jump straight in to running the example specs, open a new terminal and run the following (you can substitute `npm` for `yarn` if you prefer) -

```bash
$ yarn install
$ yarn run tests-update
$ yarn tests-start  (for starting the selenium server)


## Using environment specific values

Values can be injected in to the tests by specifying them as globals when running Jest -

```bash
$ yarn run tests -- --globals "{\"__baseUrl__\": \"https://example.com\"}
```

You can configure the default values for these globals and other Jest settings in `test/jestConfig.json`.

# Writing Specs

The spec files can be found in `tests/specs`, let's look at `index.js` as an example of how to write a simple spec -

```js
import { driver } from '../helpers';
import { load } from '../pageObjects/index';

describe('index', () => {
  it('should show the right title', async () => {
    await load();
    expect(await driver.getTitle()).toBe('Title');
  });
});
```
