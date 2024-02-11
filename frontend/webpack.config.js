const path = require('path')
var PrettierPlugin = require('webpack-prettier-plugin')

module.exports = {
  entry: './src/index.js', // entry point of your application
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, './dist'),
    devtoolModuleFilenameTemplate: '[absolute-resource-path]',
  },
  module: {
    rules: [
      {
        test: /\.scss$/,
        use: ['style-loader', 'css-loader', 'sass-loader'],
      },
    ],
  },
  devServer: {
    port: 4200,
    static: {
      directory: path.join(__dirname, './src'),
    },
    compress: false,
    client: {
      logging: 'verbose',
      overlay: true,
    },
    // Proxy requests to your backend server
    proxy: {
      '/enquiries': {
        target: 'http://localhost:5001',
        changeOrigin: true,
        // Optionally, rewrite paths if needed
        pathRewrite: {
          '^/enquiries': '', // remove '/enquiries' prefix
        },
      },
    },
    // Add CORS headers to responses
    headers: {
      'Access-Control-Allow-Origin': '*', // Adjust this to your needs
      'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
      'Access-Control-Allow-Headers':
        'X-Requested-With, content-type, Authorization',
    },
    devMiddleware: {
      writeToDisk: true,
    },
  },
  plugins: [
    new PrettierPlugin({
      printWidth: 120,
      tabWidth: 2,
      useTabs: false,
      semi: true,
      singleQuote: true,
      trailingComma: 'all',
      bracketSpacing: true,
      arrowParens: 'always',
    }),
  ],
  devtool: 'inline-source-map',
}
