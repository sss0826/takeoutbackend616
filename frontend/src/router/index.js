import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Layout from '../views/Layout.vue'

// 用户端的子页面
import CustomerHome from '../views/CustomerHome.vue'
import CustomerOrder from '../views/CustomerOrder.vue'
import CustomerCart from '../views/CustomerCart.vue'
import CustomerProfile from '../views/CustomerProfile.vue'

// 商家端页面
import MerchantStore from '../views/MerchantStore.vue'
import MerchantDish from '../views/MerchantDish.vue'

// 骑手端页面
import RiderDelivery from '../views/RiderDelivery.vue'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  
  // 用户端（带布局）
  {
    path: '/customer',
    component: Layout,
    children: [
      { path: 'home', component: CustomerHome },
      { path: 'order', component: CustomerOrder },
      { path: 'cart', component: CustomerCart },
      { path: 'profile', component: CustomerProfile },
      { path: '', redirect: 'home' }
    ]
  },
  
  // 商家端（不带布局，直接显示）
  { path: '/merchant/store', component: MerchantStore },
  { path: '/merchant/dish', component: MerchantDish },
  
  // 骑手端
  { path: '/rider/delivery', component: RiderDelivery }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router