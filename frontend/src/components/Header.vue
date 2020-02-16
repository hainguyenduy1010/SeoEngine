<template>
  <header id="header" class="navbar-fixed-top">
    <div class="header-container">
      <div class="logo search-header">
        <a href="/">
          <img :src="logo">
        </a>
      </div>

      <div id="search-box">
        <input type="text" id="search-input" class="form-control" autocomplete="off" placeholder="Search thousands of coupons and stores">
        <button class="btn-search" type="submit" title="Searching...">
          <svg x="0px" y="0px" viewBox="0 0 32 32" style="enable-background:new 0 0 32 32;" xml:space="preserve">
            <g id="Page-1">
              <g id="icon-111-search">
                <path class="searchIcon" d="M19.9,22.7c-2,1.4-4.4,2.2-7,2.2C6.1,25,0.7,19.5,0.7,12.8S6.1,0.7,12.8,0.7S25,6.1,25,12.8
                c0,2.6-0.8,5-2.2,7l8,8c0.8,0.8,0.8,2,0,2.8l0,0c-0.8,0.8-2,0.8-2.8,0L19.9,22.7L19.9,22.7z M12.8,22.1c5.1,0,9.3-4.2,9.3-9.3
                S18,3.6,12.8,3.6s-9.3,4.2-9.3,9.3S7.7,22.1,12.8,22.1L12.8,22.1z"></path>
              </g>
            </g>
          </svg>
        </button>
      </div>
      <div class="clear"></div>
    </div>
  </header>
</template>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  @import '../assets/styles/header.css';
</style>

<script>
export default {
  name: 'Home',

  data() {
    return {
      logo: require('../assets/search-logo.png')
    }
  }
}
</script>