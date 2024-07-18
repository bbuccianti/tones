local server_port = "irc.libera.chat 6667"
local channel = "#buccianti"
local url = os.getenv("JOB_URL") or "???"

local origin_job_prefix = 'https://builds.sr.ht/bbuccianti/job/'
local is_origin = url:sub(1, #origin_job_prefix) == origin_job_prefix

local branch = io.popen("git rev-parse --abbrev-ref HEAD"):read('*a')
                 :gsub('\n$', '')
local is_main = branch == 'main'

local git_log = io.popen("git log --oneline -n 1 HEAD")
local log = git_log:read("*a"):gsub("\n", " "):gsub("\n", " ")

local nc = io.popen(string.format("nc %s > /dev/null", server_port), "w")

nc:write("NICK tones-build\n")
nc:write("USER tones-build 8 x : tones-build\n")
nc:write("JOIN " .. channel .. "\n")
nc:write(string.format("PRIVMSG %s :Build failure! %s / %s\n",
                       channel, log, url))
nc:write("QUIT\n")
nc:close()
