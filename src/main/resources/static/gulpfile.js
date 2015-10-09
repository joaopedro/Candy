var gulp = require('gulp');

gulp.task('default', function() {
  // place code for your default task here
});

var webserver = require('gulp-webserver');
 
gulp.task('webserver', function() {
  gulp.src('.')
    .pipe(webserver({
      livereload: true,
      open: true,
      fallback: 'index.html'
    }));
});
