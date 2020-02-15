<template>
  <div id="app">
    
    <router-view/>

    <footer id="footer">
      <ul class="footer-nav">
        <!-- <router-link to="/about-us">About us</router-link> -
        <router-link to="/copyright">Copyright</router-link> -
        <router-link to="/disclaimer">Disclaimer</router-link> -
        <router-link to="/privacy-policy">Privacy policy</router-link> -
        <router-link to="/end-user-license-agreement">End user license agreement</router-link> -
        <router-link to="/contact-us">Contact us</router-link> -
        <router-link to="/submit-your-site">Submit your site</router-link> -->
        <li><a target="_blank" href="https://getcouponhere.com/blog/about-us">About us</a></li>
        <li><a target="_blank" href="https://getcouponhere.com/blog/copyright">Copyright</a></li>
        <li><a target="_blank" href="https://getcouponhere.com/blog/disclaimer">Disclaimer</a></li>
        <li><a target="_blank" href="https://getcouponhere.com/blog/privacy-policy">Privacy policy</a></li>
        <li><a target="_blank" href="https://getcouponhere.com/blog/end-user-license-agreement">End user license agreement</a></li>
        <li><a target="_blank" href="https://getcouponhere.com/contact">Contact us</a></li>
        <li><a target="_blank" href="https://getcouponhere.com/submit-url">Submit your site</a></li>
      </ul>
      <ul class="footer-nav">
          <li>© 2020 GetCouponHere.Com</li>
      </ul>
    </footer>
  </div>
</template>

<style>
#footer {
  position: fixed;
  z-index: 9;
  bottom: 0;
  padding: 7.5px 12px;
  background: #f2f2f2;
  border-top: 1px solid #e4e4e4;
  line-height: 40px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-sizing: border-box;
  left: 0;
}

.footer-nav, .footer-nav li  {
  display: inline-block;
  font-size: 100%;
  list-style: none;
  margin: 0;
  padding: 0;
  line-height: 17px;
}

.footer-nav li+li:before {
  content: "-";
  color: gray;
  margin: 6px;
}

.footer-nav a {
    line-height: 20px;
    padding: 0 5px;
    display: inline-block;
}
</style>
