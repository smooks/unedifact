/**
*	A maven parallelization tool for building UN/EDIFACT directories
*
*	To start it please make sure you have a node.js installed
*	
*		%> node build.js [maven parameters]
*
* 	In case no maven parameters are specified, '-o clean install' will be used.
* 	Standard output of forked child processes is stored under log directory
* 
**/
var util   = require('util'),
	path = require('path'),
	sys = require('sys'),
	fs  = require('fs'),
    spawn = require('child_process').spawn,
    exec = require('child_process').exec,
    async = require('async');

var cmdParams = process.argv.slice(2);
var MAVEN_PARAMETERS = cmdParams.length ? cmdParams : ['-o', 'clean', 'install'];

// Run maven
var mvn = function(cwd, cb) {
	path.exists(cwd, function(exists) {
		if (exists) {
			sys.puts('Starting  mvn ' + cwd);
			var procName = cwd.match('/(d[0-9]+./[a-z]+)')[1].replace('/','_');
			var logFile = process.cwd() + '/logs/' + procName + '.log';
			var logStream = fs.createWriteStream(logFile);
			var proc = spawn('mvn', MAVEN_PARAMETERS , {"cwd" : cwd });
			proc.stdout.pipe(logStream);
			proc.on('exit', function(code) {
				sys.puts('Completed mvn ' + cwd);
				cb(code);
			});
		} else {
			sys.puts('Skipping run for ' + cwd);
			cb(null);
		}
	});
}

// Sequential function that builds a single directory
var buildDir = function(directory, callback) {
	var root = process.cwd() + '/' + directory,
		start = new Date();
	console.log('Building directory ' + directory);
	var tasks = [ root + '/mapping', root + '/binding', root + '/test' ];
	async.forEachSeries(tasks, mvn, function(err) {
		if (err) {
			sys.puts('ERROR Happened when processing directory ' + directory);
		}
		callback();
 		console.log('Build for :', root, 'took\t\t', Math.round((new Date() - start) / 1000), 's');
	});
}

// Here the program starts
var dirs = [], 
	cpus = require('os').cpus().length - 1,
	logDir = process.cwd() + '/logs';

sys.puts('Starting a build process with ' + cpus + ' parallel threads');
sys.puts('Logs are stored in ' + logDir);
console.log('Launching maven parametrers "%s"', MAVEN_PARAMETERS.join(' '));
exec('rm -rf ' + logDir, function(error) {
	// Let's create a log directory
	fs.mkdirSync(logDir, 0776);
	
	// Let's create a queue with given number of concurent executions
	var q = async.queue(buildDir, cpus);
	
	// Now let's push all directories to the queue
	fs.readdirSync(process.cwd()).forEach(function(file) {
		if (file.match('d[0-9]')) {
			q.push(file);
		}
	});
});
