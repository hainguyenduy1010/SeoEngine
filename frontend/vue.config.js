module.exports = {
	devServer: {
		port: 8900,
    proxy: {
      '/api': {
        target: 'http://localhost:9000',
        ws: true,
        changeOrigin: true
      }
    },
    // historyApiFallback: true
    // historyApiFallback: {
    //   rewrites: [
    //     { from: /\/search/, to: '/index.html' },
    //     { from: /\/admin/, to: '/index.html' },
    //     { from: /\/information/, to: '/index.html' },
    //     { from: /\/favicon/, to: '/index.ico' }
    //   ]
    // }
	},
  pages: {
    'index': {
      entry: 'src/pages/home/home.js',
      title: 'GetCode - search homepage',
      template: 'public/index.html',
      chunks: ['chunk-vendors', 'chunk-common', 'index']
    },
    'search': {
      entry: 'src/pages/search-result/search-result.js',
      title: 'GetCode - search results',
      template: 'public/index.html',
      chunks: ['chunk-vendors', 'chunk-common', 'search']
    },
    'admin': {
      entry: 'src/pages/admin/admin.js',
      title: 'GetCode - admin',
      template: 'public/index.html',
      chunks: ['chunk-vendors', 'chunk-common', 'admin']
    },
    'information': {
      entry: 'src/pages/infor/infor.js',
      title: 'GetCode - information',
      template: 'public/index.html',
      chunks: ['chunk-vendors', 'chunk-common', 'information']
    }
  },
	runtimeCompiler: true,
	outputDir: 'target/dist',
  assetsDir: 'static',
  chainWebpack: config => {
    config.module
      .rule('raw')
      .test(/\.txt$/)
      .use('raw-loader')
      .loader('raw-loader')
      .end()
  }
};