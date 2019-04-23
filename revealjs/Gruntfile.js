/* global module:false */
module.exports = function(grunt) {
  var port = grunt.option('port') || 8000;
  // Project configuration
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    meta: {
      banner:
        '/*!\n' +
        ' * reveal.js <%= pkg.version %> (<%= grunt.template.today("yyyy-mm-dd, HH:MM") %>)\n' +
        ' * http://lab.hakim.se/reveal-js\n' +
        ' * MIT licensed\n' +
        ' *\n' +
        ' * Copyright (C) 2015 Hakim El Hattab, http://hakim.se\n' +
        ' */'
    },

    qunit: {
      files: [ 'test/*.html' ]
    },

    uglify: {
      options: {
        banner: '<%= meta.banner %>\n'
      },
      build: {
        src: 'js/reveal.js',
        dest: 'js/reveal.min.js'
      }
    },

    sass: {
      core: {
        files: {
          'css/reveal.css': 'css/reveal.scss',
        }
      },
      themes: {
        files: {
          'css/theme/puzzle.css': 'css/theme/source/puzzle.scss',
          // 'css/theme/black.css': 'css/theme/source/black.scss',
          // 'css/theme/white.css': 'css/theme/source/white.scss',
          // 'css/theme/league.css': 'css/theme/source/league.scss',
          // 'css/theme/beige.css': 'css/theme/source/beige.scss',
          // 'css/theme/night.css': 'css/theme/source/night.scss',
          // 'css/theme/serif.css': 'css/theme/source/serif.scss',
          // 'css/theme/simple.css': 'css/theme/source/simple.scss',
          // 'css/theme/sky.css': 'css/theme/source/sky.scss',
          // 'css/theme/moon.css': 'css/theme/source/moon.scss',
          // 'css/theme/solarized.css': 'css/theme/source/solarized.scss',
          // 'css/theme/blood.css': 'css/theme/source/blood.scss'
        }
      }
    },

    autoprefixer: {
      dist: {
        src: 'css/reveal.css'
      },
      themes: {
        src: 'css/theme/puzzle.css'
      }
    },

    cssmin: {
      my_target: {
        files: {
          'css/reveal.min.css': [ 'css/reveal.css' ]
        }
      }
    },

    jshint: {
      options: {
        curly: false,
        eqeqeq: true,
        immed: true,
        latedef: true,
        newcap: true,
        noarg: true,
        sub: true,
        undef: true,
        eqnull: true,
        browser: true,
        expr: true,
        globals: {
          head: false,
          module: false,
          console: false,
          unescape: false,
          define: false,
          exports: false
        }
      },
      files: [ 'Gruntfile.js', 'js/reveal.js' ]
    },

    connect: {
      server: {
        options: {
          port: port,
          base: '.',
          livereload: true,
          open: true
        }
      }
    },

    svgmin: {
      options: {
        plugins: [{
          removeViewBox: false
        }, {
          removeUselessStrokeAndFill: false
        },{
          cleanupIDs: false
        }]
      },
      dist: {
        files: [{
          expand: true,
          cwd: 'lib/svg/',
          src: ['*.svg'],
          dest: 'lib/svg/'
        }]
      }
    },
    svginject: {
      all : {
        options: {},
        files: {
           'js/SVGinject.js': ['lib/svg/*.svg']
        }
      }
    },


    zip: {
      'reveal-js-presentation.zip': [
        'index.html',
        'css/**',
        'js/**',
        'lib/**',
        'images/**',
        'plugin/**'
      ]
    },

    watch: {
            options: {
                livereload: true
            },
      js: {
        files: [ 'Gruntfile.js', 'js/reveal.js' ],
        tasks: 'js'
      },
      theme: {
        files: [ 'css/theme/source/*.scss', 'css/theme/template/*.scss' ],
        tasks: 'css-themes'
      },
      css: {
        files: [ 'css/reveal.scss' ],
        tasks: 'css-core'
      },
      svg: {
        files: [ 'lib/svg/*.svg' ],
        tasks: ['svgmin', 'svginject']
      },
      html: {
          files: [ 'index.html']
      },
      md: {
          files: [ '*.md']
      }
    }

  });

  // Dependencies
  grunt.loadNpmTasks( 'grunt-contrib-qunit' );
  grunt.loadNpmTasks( 'grunt-contrib-jshint' );
  grunt.loadNpmTasks( 'grunt-css' );
  grunt.loadNpmTasks( 'grunt-contrib-uglify' );
  grunt.loadNpmTasks( 'grunt-contrib-watch' );
  grunt.loadNpmTasks( 'grunt-sass' );
  grunt.loadNpmTasks( 'grunt-contrib-connect' );
  grunt.loadNpmTasks( 'grunt-autoprefixer' );
  grunt.loadNpmTasks( 'grunt-zip' );
  grunt.loadNpmTasks( 'grunt-svginject' );
  grunt.loadNpmTasks( 'grunt-svgmin' );


  // Default task
  grunt.registerTask( 'default', [ 'css', 'js', 'svginject' ] );

  // JS task
  grunt.registerTask( 'js', [ 'jshint', 'uglify', 'qunit' ] );

  // Theme CSS
  grunt.registerTask( 'css-themes', [ 'sass:themes', 'autoprefixer:themes'] );

  // Core framework CSS
  grunt.registerTask( 'css-core', [ 'sass:core', 'autoprefixer:dist', 'cssmin' ] );

  // All CSS
  grunt.registerTask( 'css', [ 'sass', 'autoprefixer:dist', 'cssmin' ] );

  // Package presentation to archive
  grunt.registerTask( 'package', [ 'default', 'zip' ] );

  // Serve presentation locally
  grunt.registerTask( 'serve', [ 'connect', 'watch' ] );

  // Run tests
  grunt.registerTask( 'test', [ 'jshint', 'qunit' ] );

};
