<template>
  <div class="layout">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="logo">
        <span class="logo-icon">🍔</span>
        <span class="logo-text">餐享</span>
      </div>
      <div class="nav-links">
  <span class="nav-link" :class="{ active: activeMenu === '/customer/home' }" @click="goTo('/customer/home')">首页</span>
  <span class="nav-link" :class="{ active: activeMenu === '/customer/order' }" @click="goTo('/customer/order')">点餐</span>
  <span class="nav-link" :class="{ active: activeMenu === '/customer/cart' }" @click="goTo('/customer/cart')">购物车</span>
  <span class="nav-link" :class="{ active: activeMenu === '/customer/profile' }" @click="goTo('/customer/profile')">我的</span>
</div>
      <div class="user-info">
        <span class="username">{{ username }}</span>
        <el-button type="danger" size="small" plain @click="logout">退出</el-button>
      </div>
    </div>

    <div class="main-layout">
      <!-- 左侧菜单栏 -->
      <div class="sidebar">
        <div class="user-avatar">
          <div class="avatar">🐱</div>
          <div class="user-name">{{ username }}</div>
          <div class="user-level">黄金会员</div>
        </div>
        
        <el-menu :default-active="activeMenu" @select="handleMenuSelect" class="sidebar-menu">
          <el-menu-item index="/customer/home">
            <el-icon><House /></el-icon>
            <span>首页</span>
          </el-menu-item>
          <el-menu-item index="/customer/order">
            <el-icon><Food /></el-icon>
            <span>点餐</span>
          </el-menu-item>
         <el-menu-item index="/customer/cart">
  <el-icon><ShoppingCart /></el-icon>
  <span>购物车</span>
</el-menu-item>
          <el-menu-item index="/customer/profile">
            <el-icon><User /></el-icon>
            <span>我的</span>
          </el-menu-item>
        </el-menu>

        <div class="sidebar-footer">
          <div class="coupon-tip">
            <span>🎁</span>
            <span>领取优惠券</span>
          </div>
        </div>
      </div>

      <div class="content">
        <router-view />
      </div>
    </div>

    <div class="footer">
      <p>© 2024 餐享网上订餐平台 | 让美食触手可及</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { House, Food, ShoppingCart, User } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const username = ref('美食家小明')
const cartCount = ref(0)

// 当前激活的菜单项（监听路由变化自动更新）
const activeMenu = ref(route.path)

// 监听路由变化，同步更新顶部和左侧的激活状态
watch(() => route.path, (newPath) => {
  activeMenu.value = newPath
}, { immediate: true })

const handleMenuSelect = (index) => {
  router.push(index)
}

const goTo = (path) => {
  router.push(path)
}

const updateCartCount = () => {
  const cart = JSON.parse(localStorage.getItem('cart') || '[]')
  cartCount.value = cart.reduce((sum, item) => sum + item.quantity, 0)
}

onMounted(() => {
  updateCartCount()
  window.addEventListener('cart-updated', () => updateCartCount())
})

const logout = () => {
  router.push('/login')
}
</script>

<style scoped>
.layout {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: #FFF8F0;
}

.header {
  height: 70px;
  background: linear-gradient(135deg, #FFE4B5 0%, #FFD699 100%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 32px;
  box-shadow: 0 2px 12px rgba(255, 150, 50, 0.15);
  border-bottom: 1px solid #FFD280;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.logo-icon {
  font-size: 32px;
}

.logo-text {
  font-size: 24px;
  font-weight: bold;
  color: #D2691E;
  letter-spacing: 2px;
}

.nav-links {
  display: flex;
  gap: 32px;
}

.nav-link {
  font-size: 16px;
  color: #D2691E;
  cursor: pointer;
  padding: 8px 12px;
  transition: all 0.3s;
  font-weight: 500;
  position: relative;
  display: inline-flex;
  align-items: center;
  gap: 6px;
  border-radius: 8px;
}

.nav-link:hover {
  color: #FF8C00;
  background: rgba(255, 107, 53, 0.08);
}

/* 顶部激活样式 */
.nav-link.active {
  color: #FF6B35;
  font-weight: bold;
  background: rgba(255, 107, 53, 0.15);
}

.cart-badge {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-width: 18px;
  height: 18px;
  background: #FF4444;
  color: white;
  font-size: 11px;
  font-weight: bold;
  border-radius: 10px;
  padding: 0 5px;
  margin-left: 4px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 20px;
}

.username {
  color: #D2691E;
  font-weight: 500;
}

.main-layout {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.sidebar {
  width: 260px;
  background: white;
  border-right: 1px solid #FFE0B5;
  display: flex;
  flex-direction: column;
  box-shadow: 2px 0 8px rgba(0,0,0,0.02);
}

.user-avatar {
  text-align: center;
  padding: 24px 20px;
  background: linear-gradient(135deg, #FFF4E6 0%, #FFE8D4 100%);
  margin-bottom: 16px;
}

.avatar {
  width: 70px;
  height: 70px;
  background: linear-gradient(135deg, #FFD699 0%, #FFB347 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 40px;
  margin: 0 auto 12px;
  box-shadow: 0 4px 12px rgba(255, 150, 50, 0.2);
}

.user-name {
  font-size: 16px;
  font-weight: bold;
  color: #D2691E;
}

.user-level {
  font-size: 12px;
  color: #FF8C42;
  margin-top: 4px;
}

.sidebar-menu {
  flex: 1;
  border-right: none;
  background: transparent;
}

.sidebar-menu .el-menu-item {
  height: 56px;
  line-height: 56px;
  margin: 4px 12px;
  border-radius: 12px;
  transition: all 0.3s;
}

.sidebar-menu .el-menu-item:hover {
  background: #FFF4E6;
  color: #FF8C42;
}

.sidebar-menu .el-menu-item.is-active {
  background: linear-gradient(135deg, #FFE4B5 0%, #FFD699 100%);
  color: #D2691E;
  font-weight: bold;
}

.menu-badge {
  margin-left: auto;
}

.sidebar-footer {
  padding: 20px;
  margin: 16px;
  background: linear-gradient(135deg, #FFF4E6 0%, #FFE8D4 100%);
  border-radius: 12px;
  text-align: center;
}

.coupon-tip {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  color: #FF8C42;
  font-weight: bold;
  cursor: pointer;
}

.coupon-tip span:first-child {
  font-size: 20px;
}

.content {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
  background: #FFF8F0;
}

.footer {
  height: 48px;
  background: #FFE8D4;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  color: #D2691E;
  border-top: 1px solid #FFDBA8;
}

.footer p {
  margin: 0;
}
</style>