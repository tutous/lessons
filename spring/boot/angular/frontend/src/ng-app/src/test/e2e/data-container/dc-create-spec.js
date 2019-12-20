const DcPage = require('./dc-page.variables');
const path = require('path');

describe('DataContainerCreate Test', function() {
  it('should create a new DC and find it in DataContainerView result', function() {
    browser.get('http://localhost:4200/dc/create');

    DcPage.nameInput.sendKeys('My new DC 01');
    DcPage.dcTypeOptions.first().click();
    DcPage.vehicleClassInput.sendKeys('ABC');
    const absolutePath = path.resolve('./data-container/testFile.txt')
    DcPage.fileInput.sendKeys(absolutePath);
    DcPage.descriptionInput.sendKeys('Test Beschreibung');

    DcPage.saveButton.click();

    result = element.all(by.tagName('td')).filter(function(elem) {
      return elem.getText().then(function(text) {
        return text === 'My new DC 01';
      })
    });

    expect(result.isPresent()).toBe(true);
  });

});
