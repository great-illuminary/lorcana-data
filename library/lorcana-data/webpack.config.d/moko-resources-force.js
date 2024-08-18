// noinspection JSUnnecessarySemicolon
;(function(config) {
    const fs = require("fs");

    const path = require('path');
    const MiniCssExtractPlugin = require('mini-css-extract-plugin');


    function containsGradlew(absoluteFolder) {
        const list = fs.readdirSync(absoluteFolder);
        return list.find(child => child === "gradlew");
    }

    function findRootWithGradlew(currentPath) {
        if (containsGradlew(currentPath)) return currentPath;
        return findRootWithGradlew(`${currentPath}/../`);
    }

    // the actualRoot is actually normally always from this
    const actualRoot = findRootWithGradlew(path.resolve("."));

console.log(actualRoot)

    const relativeModulesToLookUp = [
        "lorcana-data"
    ];

    const jsMainsRes = relativeModulesToLookUp
        .map(folder => {
            // going from jsMain to ...
            const absoluteFolder = path.resolve(`${actualRoot}/library/${folder}/build/generated/moko-resources/jsMain/`)
            const list = fs.readdirSync(absoluteFolder);

            // now lookup for the one folder where the sub res/ do exists
            const child = list.find(child => {
                const absolutePath = path.resolve(`${absoluteFolder}/${child}`);
                return fs.readdirSync(absolutePath).find(folder => folder == "res");
            })

            if (!child) {
                return null;
            }

            // ... jsMain/child/res
            return path.resolve(`${absoluteFolder}/${child}/res`);
        }).filter(child => !!child);

    config.module.rules.push(
        {
            test: /\.(.*)/,
            resource: jsMainsRes.map(resourcePath => {
                return ["files", "images", "localization"].map(child => path.resolve(resourcePath, child));
            }).flat(),
            type: 'asset/resource',
            generator: {
              filename: "assets/[name][ext]",
            },
        }
    );


    config.module.rules.push(
        {
            test: /\.(.*)/,
            resource: jsMainsRes.map(resourcePath => {
                return ["files"].map(child => path.resolve(resourcePath, child));
            }).flat(),
            type: 'asset/resource',
            generator: {
              filename: "files/[name][ext]",
            },
        }
    );

    config.plugins.push(new MiniCssExtractPlugin())
    config.module.rules.push(
        {
            test: /\.css$/,
            resource: jsMainsRes.map(resPath => path.resolve(resPath, "fonts")),
            use: ['style-loader', 'css-loader']
        }
    )

    config.module.rules.push(
        {
            test: /\.(otf|ttf)?$/,
            resource: jsMainsRes.map(resPath => path.resolve(resPath, "fonts")),
            type: 'asset/resource',
        }
    )

console.log("jsMainsRes", jsMainsRes)
    jsMainsRes.forEach(path => config.resolve.modules.push(path));
})(config);