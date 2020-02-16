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
	runtimeCompiler: true,
	outputDir: 'target/dist',
  assetsDir: 'static'
};