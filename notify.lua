local server_port = "irc.libera.chat 6667"
local channel = "#buccianti"
local nick = "tones"
local url = os.getenv("JOB_URL") or "???"
local result = (arg[1] == 0 or arg[1] == "0") and  "SUCCEEDED" or "FAILED"

local origin_job_prefix = 'https://builds.sr.ht/bbuccianti/job/'
local is_origin = url:sub(1, #origin_job_prefix) == origin_job_prefix

local branch = io.popen("git rev-parse --abbrev-ref HEAD"):read('*a')
                 :gsub('\n$', '')
local is_main = branch == 'main'

local git_log = io.popen("git log --oneline -n 1 HEAD")
local log = git_log:read("*a"):gsub("\n", " "):gsub("\n", " ")

local nc = io.popen(string.format("nc -v %s > /dev/null", server_port), "w")

nc:write(string.format("NICK %s\n", nick))
nc:write(string.format("USER %s 8 x : %s\n", nick, nick))
nc:write("JOIN " .. channel .. "\n")
nc:write(string.format("PRIVMSG %s :Build %s! @fold - %s / %s\n",
                       channel, result, log, url))
nc:write("QUIT\n")
nc:close()
