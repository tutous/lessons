// conf.js
exports.config = {
  framework: 'jasmine',
  seleniumAddress: 'http://localhost:4444/wd/hub',
  specs: [
    './data-container/dc-create-spec.js',
  ],
  capabilities: {
    browserName: 'firefox'
  }
}
