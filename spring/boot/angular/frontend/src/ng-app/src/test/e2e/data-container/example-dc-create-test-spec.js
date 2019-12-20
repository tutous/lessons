
describe('DataContainerCreate Test', function() {
  var nameInput = element(by.id('name'));
  var nameRequiredDiv = element(by.id('nameRow.required'));

  var dcTypeSelect = element(by.id('type'));
  var dcTypeOptions = dcTypeSelect.all(by.tagName('option'));

  var vehicleClassInput = element(by.id('vehicleClasses'));
  var vehicleClassCustomErroMessageDiv = element(by.id('vehicleClassesRow.customErroMessage'));
  var vehicleClassRequiredDiv = element(by.id('vehicleClassesRow.required'));

  // beforeEach(function() {
  //   browser.get('http://localhost:4200/dc/create');
  // });

  // Test 01
  it('should have a page title', function() {
    browser.get('http://localhost:4200/dc/create');
    expect(browser.getTitle()).toEqual('Konditor');
  });

  // Test 02
  it('should have required name input field', function() {
    // empty input field has to be invalid and initially required message should not be displayed
    expect(nameInput.getAttribute('class')).toContain('ng-invalid');
    expect(nameRequiredDiv.isPresent()).toBe(false);

    // entering some value and input field must be valid
    nameInput.sendKeys('Test');
    expect(nameInput.getAttribute('value')).toEqual('Test');
    expect(nameInput.getAttribute('class')).toContain('ng-valid');
    expect(nameRequiredDiv.isPresent()).toBe(false);

    // clearing value and input field must be invalid
    nameInput.clear();
    nameInput.sendKeys(' ');
    nameInput.sendKeys(protractor.Key.BACK_SPACE);
    expect(nameInput.getAttribute('class')).toContain('ng-invalid');
    expect(nameRequiredDiv.isPresent()).toBe(true);
  });

  // Test 03
  it('should have required type select field', function() {
    // empty select menu must be invalid
    expect(dcTypeSelect.getAttribute('class')).toContain('ng-invalid');

    // first option must be UDS
    expect(dcTypeOptions.first().getText()).toEqual('UDS');

    // select first option UDS and select menu must be valid
    dcTypeOptions.first().click();
    expect(dcTypeSelect.getAttribute('class')).toContain('ng-valid');
  });

  // Test 04
  it('should have required and validated vehicleClass input field', function() {
    // empty input field has to be invalid
    expect(vehicleClassInput.getAttribute('class')).toContain('ng-invalid');
    expect(vehicleClassRequiredDiv.isPresent()).toBe(false);
    expect(vehicleClassCustomErroMessageDiv.isPresent()).toBe(false);

    // entering valid value and input field must be valid
    vehicleClassInput.sendKeys('ABC');
    expect(vehicleClassInput.getAttribute('value')).toEqual('ABC');
    expect(vehicleClassInput.getAttribute('class')).toContain('ng-valid');
    expect(vehicleClassRequiredDiv.isPresent()).toBe(false);
    expect(vehicleClassCustomErroMessageDiv.isPresent()).toBe(false);

    // entering invalid value and input field must be invalid and custom error message should be displayed
    vehicleClassInput.clear();
    vehicleClassInput.sendKeys('XYZ');
    expect(vehicleClassInput.getAttribute('value')).toEqual('XYZ');
    expect(vehicleClassInput.getAttribute('class')).toContain('ng-invalid');
    expect(vehicleClassRequiredDiv.isPresent()).toBe(false);
    expect(vehicleClassCustomErroMessageDiv.isPresent()).toBe(true);

    vehicleClassInput.clear();
    vehicleClassInput.sendKeys(' ');
    vehicleClassInput.sendKeys(protractor.Key.BACK_SPACE);
    expect(vehicleClassInput.getAttribute('class')).toContain('ng-invalid');
    expect(vehicleClassRequiredDiv.isPresent()).toBe(true);
    expect(vehicleClassCustomErroMessageDiv.isPresent()).toBe(false);

    // browser.sleep(5000);
  });

});
