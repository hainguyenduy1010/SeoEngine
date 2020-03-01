module.exports = {
	devServer: {
		port: 8900,
    proxy: {
      '/api': {
        target: 'http://localhost:9000',
        ws: true,
        changeOrigin: true
      }
    }
	},
  pages: {
    'index': {
      entry: 'src/pages/home/home.js',
      title: 'Search Homepage',
      template: 'public/index.html',
      chunks: ['chunk-vendors', 'chunk-common', 'index']
    },
    'search': {
      entry: 'src/pages/search-result/search-result.js',
      title: 'Search Results',
      template: 'public/index.html',
      chunks: ['chunk-vendors', 'chunk-common', 'search']
    },
    'admin': {
      entry: 'src/pages/admin/admin.js',
      title: 'Admin',
      template: 'public/index.html',
      chunks: ['chunk-vendors', 'chunk-common', 'admin']
    }
  },
	runtimeCompiler: true,
	outputDir: 'target/dist',
  assetsDir: 'static'
};