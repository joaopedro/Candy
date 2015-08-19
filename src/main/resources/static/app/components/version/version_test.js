'use strict';

describe('candyApp.version module', function() {
  beforeEach(module('candyApp.version'));

  describe('version service', function() {
    it('should return current version', inject(function(version) {
      expect(version).toEqual('0.1');
    }));
  });
});
