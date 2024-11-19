# Redux Demo

First you want to create a simple typescript application

```
mkdir redux
cd redux
npm init -y
npm install typescript --save-dev
npx tsc --init
```

Edit the file `tsconfig.json` and place the contents:

```
{
  "compilerOptions": {
    "target": "es6",                 
    "module": "commonjs",            
    "strict": true,                  
    "outDir": "./dist",              
    "rootDir": "./src"               
  }
}
```

Edit `package.json` and replace the node `scripts` with 

```
"scripts": {
  "build": "tsc",
  "start": "node dist/index.js"
}
```

Then, create a source folder named `src` and edit a file called `index.ts` with the content:

```
console.log("Hello World!")
```

Now, in the folder of the application you can run 

```
npm run build
npm start
```

