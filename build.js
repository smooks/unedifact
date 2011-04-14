var util   = require('util'),
	path = require('path'),
	sys = require('sys'),
	fs  = require('fs'),
    spawn = require('child_process').spawn,
    exec = require('child_process').exec,
    async = require('async');

// Run maven
var mvn = function(cwd, cb) {
	path.exists(cwd, function(exists) {
		if (exists) {
			sys.puts('Starting  mvn ' + cwd);
			var procName = cwd.match('/(d[0-9]+./[a-z]+)')[1].replace('/','_');
			var logFile = process.cwd() + '/log/' + procName + '.log';
			var logStream = fs.createWriteStream(logFile);
			var proc = spawn('mvn', ['-o', 'clean'], {"cwd" : cwd });
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
 		console.log('Build for :', root, 'took\t\t', new Date() - start, 'ms');
	});
}

// Here the program starts
var dirs = [], 
	cpus = require('os').cpus().length - 1,
	logDir = process.cwd() + '/log';

sys.puts('Starting a build process with ' + cpus + ' parallel threads');
sys.puts('Logs are stored in ' + logDir);
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
