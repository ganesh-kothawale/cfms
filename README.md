# kotlin-project-template-repo
This is a template for creating a Kotlin micro service.

To setup your repo follow below steps

1. Create your repo on GitHub using this repo as the template repo as mentioned [Here](https://github.blog/2019-06-06-generate-new-repositories-with-repository-templates/)
2. Clone your new repo to your local setup
3. At the root directory of your repository run `bash init.sh`
4. Enter your service name when script prompts.

That's all.
Your repo is ready with most of the boiler plate code we use accross repos.

In `servers` folder by default you get follwing modules along with their deployment scripts pre configured.
1. `ktor` - for internal rest APIs 
2. `ktor-external` - for external rest APIs
3. `sqs` - for asynchronous jobs. 
4. `commons` module which hosts common logic for above 3 modules
5. `client` module if you ever need to expose your APIs or communicate with your sqs server from ktor servers.

You can delete the modules which you don't need right now, or you can keep them for your future usecase. 
