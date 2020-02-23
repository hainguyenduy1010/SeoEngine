<template>
  <div id="app">
    <div class="container">

        <div class="home-logo">
          <a href="/">
            <img :src="logo">
          </a>
        </div>

        <div id="search-box">
          <form @submit.prevent="searchSubmit">
            <input id="search-input" placeholder="Search thousands of coupons and stores" v-model="keyword">
            <!-- <router-link :to="{name: 'search', params: {keyword}}"> -->
            <!-- <a v-bind:href="'/search?k=' + keyword"> -->
              <button class="btn-search" type="submit">
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
            <!-- </a> -->
          <!-- </router-link> -->
          </form>
        </div>

        <div class="toolbars">
          <ul>
            <li>
              <a href="https://mail.google.com" class="toolbar" target="_blank" data-toolbar-id="29">
                <img src="@/assets/images/toolbar/gmail.png" style="max-height: 44px; max-width: 100px;">
              </a>
            </li>
            <li>
              <a href="https://www.yahoo.com/" class="toolbar" target="_blank" data-toolbar-id="28">
                <img src="@/assets/images/toolbar/yahoo.png" style="max-height: 44px; max-width: 100px;">
              </a>
            </li>
            <li>
              <a href="https://www.facebook.com/" class="toolbar" target="_blank" data-toolbar-id="27">
                <img src="@/assets/images/toolbar/facebook.png" style="max-height: 44px; max-width: 100px;">
              </a>
            </li>
            <li>
              <a href="https://www.youtube.com/" class="toolbar" target="_blank" data-toolbar-id="26">
                <img src="@/assets/images/toolbar/youtube.png" style="max-height: 44px; max-width: 100px;">
              </a>
          </li>
            <li>
              <a href="https://www.amazon.com/" class="toolbar" target="_blank" data-toolbar-id="25">
                <img src="@/assets/images/toolbar/amazon.png" style="max-height: 44px; max-width: 100px;">
              </a>
          </li>
            <li>
              <a href="https://www.trivago.com" class="toolbar" target="_blank" data-toolbar-id="18">
                <img src="@/assets/images/toolbar/chiclet_trivago.png" style="max-height: 44px; max-width: 100px;">
              </a>
          </li>
            <li>
              <a href="https://www.booking.com/" class="toolbar" target="_blank" data-toolbar-id="19">
                <img src="@/assets/images/toolbar/chiclet_booking.png" style="max-height: 44px; max-width: 100px;">
              </a>
          </li>
            <li>
              <a href="https://www.priceline.com/" class="toolbar" target="_blank" data-toolbar-id="20">
                <img src="@/assets/images/toolbar/chiclet_priceline.png" style="max-height: 44px; max-width: 100px;">
              </a>
          </li>
            <li>
              <a href="http://www.macys.com/" class="toolbar" target="_blank" data-toolbar-id="21">
                <img src="@/assets/images/toolbar/macys.png" style="max-height: 44px; max-width: 100px;">
              </a>
          </li>
            <li>
              <a href="https://www.target.com/" class="toolbar" target="_blank" data-toolbar-id="22">
                <img src="@/assets/images/toolbar/target.png" style="max-height: 44px; max-width: 100px;">
              </a>
          </li>
            <li>
              <a href="https://www.tripadvisor.com/" class="toolbar" target="_blank" data-toolbar-id="23">
                <img src="@/assets/images/toolbar/tripadvisor.png" style="max-height: 44px; max-width: 100px;">
              </a>
          </li>
            <li>
              <a href="https://www.walmart.com/" class="toolbar" target="_blank" data-toolbar-id="24">
                <img src="@/assets/images/toolbar/walmart.png" style="max-height: 44px; max-width: 100px;">
              </a>
          </li>
            <li>
              <a href="https://www.retailmenot.com/" class="toolbar" target="_blank" data-toolbar-id="30">
                <img src="@/assets/images/toolbar/retailmenot.jpg" style="max-height: 44px; max-width: 100px;">
              </a>
          </li>
                
        </ul>
      </div>
    </div>
    
    <Footer></Footer>
  </div>
</template>

<style>
  @import '../../assets/styles/main.css';
</style>

<script src="./home.js"></script>
