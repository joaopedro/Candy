'use strict';

angular.module('candyApp.version', [
  'candyApp.version.interpolate-filter',
  'candyApp.version.version-directive'
])

.value('version', '0.2');
