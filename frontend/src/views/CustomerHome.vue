<template>
  <div class="customer-home">
    <!-- 欢迎横幅 -->
    <div class="welcome-card">
      <div class="welcome-text">
        <h2>🍔 饿了么？来餐享！</h2>
        <p>精选美食，30分钟极速送达</p>
      </div>
      <div class="stats">
        <div class="stat-item">
          <span class="stat-number">128+</span>
          <span class="stat-label">合作商家</span>
        </div>
        <div class="stat-item">
          <span class="stat-number">5000+</span>
          <span class="stat-label">用户好评</span>
        </div>
        <div class="stat-item">
          <span class="stat-number">30min</span>
          <span class="stat-label">平均送达</span>
        </div>
      </div>
    </div>

    <!-- 热门推荐（自动滚动） -->
    <div class="section">
      <div class="section-header">
        <h2>🔥 热门推荐</h2>
        <div class="scroll-controls">
          <el-button size="small" circle @click="scrollPrev" :disabled="scrollIndex === 0">◀</el-button>
          <span class="scroll-indicator">{{ scrollIndex + 1 }} / {{ totalPages }}</span>
          <el-button size="small" circle @click="scrollNext" :disabled="scrollIndex >= totalPages - 1">▶</el-button>
          <span class="more" @click="showAllStores">查看全部 ></span>
        </div>
      </div>
      <div class="auto-scroll-container" ref="scrollContainer">
        <div class="scroll-wrapper" :style="{ transform: `translateX(-${scrollIndex * 100}%)` }">
          <div class="scroll-page" v-for="(page, pageIndex) in storePages" :key="pageIndex">
            <div class="store-card" v-for="store in page" :key="store.id" @click="goToStore(store.id)">
              <div class="store-img" :style="{ backgroundImage: `url(${store.img})` }">
                <span class="rating">{{ store.rating }}⭐</span>
                <span class="delivery-time">{{ store.time }}分钟</span>
              </div>
              <div class="store-info">
                <h3>{{ store.name }}</h3>
                <p>{{ store.foodType }}</p>
                <p class="sales">月销 {{ store.sales }}+</p>
                <p class="min-price">¥{{ store.minPrice }}起送</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 美食分类 -->
    <div class="section">
      <div class="section-header">
        <h2>🍜 美食分类</h2>
      </div>
      <div class="category-grid">
        <div class="category-item" v-for="cat in categories" :key="cat.name" @click="filterByCategory(cat.name)">
          <div class="category-icon" :style="{ background: cat.bgColor }">{{ cat.icon }}</div>
          <span>{{ cat.name }}</span>
        </div>
      </div>
    </div>

    <!-- 附近门店 -->
    <div class="section">
      <div class="section-header">
        <h2>📍 附近门店</h2>
        <span class="more" @click="showAllStores">查看更多 ></span>
      </div>
      <div class="store-list">
        <div class="store-item" v-for="store in nearbyStores" :key="store.id" @click="goToStore(store.id)">
          <div class="store-avatar" :style="{ backgroundImage: `url(${store.avatar})` }"></div>
          <div class="store-detail">
            <h3>{{ store.name }}</h3>
            <p>{{ store.address }}</p>
            <div class="store-stats">
              <span>⭐ {{ store.rating }}</span>
              <span>月销 {{ store.sales }}</span>
              <span>¥{{ store.minPrice }}起送</span>
            </div>
          </div>
          <el-button type="primary" plain size="small" class="enter-btn">进店</el-button>
        </div>
      </div>
    </div>

    <!-- 全部店铺弹窗 -->
    <el-dialog v-model="allStoresVisible" title="全部商家" width="900px">
      <div class="all-stores-grid">
        <div class="store-card-item" v-for="store in allStores" :key="store.id" @click="goToStore(store.id); allStoresVisible = false">
          <div class="store-img-small" :style="{ backgroundImage: `url(${store.img})` }"></div>
          <div class="store-info-small">
            <h4>{{ store.name }}</h4>
            <p>{{ store.foodType }}</p>
            <p class="price">¥{{ store.minPrice }}起送</p>
          </div>
        </div>
      </div>
    </el-dialog>

    <!-- 店铺详情弹窗 -->
    <el-dialog v-model="storeDetailVisible" :title="currentStore.name" width="800px" class="store-detail-dialog" @close="closeStoreDetail">
      <div class="store-detail-content">
        <div class="store-banner" :style="{ backgroundImage: `url(${currentStore.banner})` }"></div>
        <div class="store-info-detail">
          <div class="store-meta">
            <span>⭐ {{ currentStore.rating }}</span>
            <span>月销 {{ currentStore.sales }}+</span>
            <span>¥{{ currentStore.minPrice }}起送</span>
            <span>{{ currentStore.time }}分钟</span>
          </div>
          <p class="store-address">{{ currentStore.address }}</p>
        </div>
        
        <div class="menu-section">
          <h3>推荐菜品</h3>
          <div class="dish-list">
            <div class="dish-item" v-for="dish in currentStore.dishes" :key="dish.id">
              <div class="dish-img" :style="{ backgroundImage: `url(${dish.img})` }"></div>
              <div class="dish-info">
                <h4>{{ dish.name }}</h4>
                <p>{{ dish.description }}</p>
                <p class="dish-price">¥{{ dish.price }}</p>
              </div>
              <div class="dish-actions">
                <el-input-number v-model="dish.quantity" :min="0" :max="99" size="small" @change="updateCart(dish)" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'

const allStoresVisible = ref(false)
const storeDetailVisible = ref(false)

// 自动滚动相关
const scrollContainer = ref(null)
const scrollIndex = ref(0)
const itemsPerPage = 4
const autoScrollTimer = ref(null)

// 热门推荐8个店铺
const hotStores = ref([
  { id: 1, name: '肯德基', foodType: '炸鸡汉堡', rating: 4.8, sales: 2345, time: 30, minPrice: 20, img: 'https://images.pexels.com/photos/2983101/pexels-photo-2983101.jpeg?w=200&h=130&fit=crop' },
  { id: 2, name: '喜茶', foodType: '奶茶果茶', rating: 4.9, sales: 1890, time: 25, minPrice: 15, img: 'https://images.pexels.com/photos/2392977/pexels-photo-2392977.jpeg?w=200&h=130&fit=crop' },
  { id: 3, name: '海底捞', foodType: '川味火锅', rating: 4.9, sales: 1234, time: 45, minPrice: 50, img: 'https://images.pexels.com/photos/7231975/pexels-photo-7231975.jpeg?w=200&h=130&fit=crop' },
  { id: 4, name: '必胜客', foodType: '披萨意面', rating: 4.7, sales: 987, time: 35, minPrice: 40, img: 'https://images.pexels.com/photos/825661/pexels-photo-825661.jpeg?w=200&h=130&fit=crop' },
  { id: 5, name: '星巴克', foodType: '咖啡甜品', rating: 4.8, sales: 1567, time: 20, minPrice: 25, img: 'https://images.pexels.com/photos/312418/pexels-photo-312418.jpeg?w=200&h=130&fit=crop' },
  { id: 6, name: '西贝', foodType: '西北菜', rating: 4.6, sales: 876, time: 40, minPrice: 35, img: 'https://images.pexels.com/photos/1279330/pexels-photo-1279330.jpeg?w=200&h=130&fit=crop' },
  { id: 7, name: '奈雪', foodType: '茶饮欧包', rating: 4.8, sales: 1456, time: 25, minPrice: 20, img: 'https://images.pexels.com/photos/3026810/pexels-photo-3026810.jpeg?w=200&h=130&fit=crop' },
  { id: 8, name: '太二', foodType: '酸菜鱼', rating: 4.7, sales: 1098, time: 35, minPrice: 45, img: 'https://images.pexels.com/photos/2010749/pexels-photo-2010749.jpeg?w=200&h=130&fit=crop' }
])

// 分页计算
const storePages = computed(() => {
  const pages = []
  for (let i = 0; i < hotStores.value.length; i += itemsPerPage) {
    pages.push(hotStores.value.slice(i, i + itemsPerPage))
  }
  return pages
})

const totalPages = computed(() => storePages.value.length)

// 滚动控制
const scrollNext = () => {
  if (scrollIndex.value < totalPages.value - 1) {
    scrollIndex.value++
    resetAutoScroll()
  }
}

const scrollPrev = () => {
  if (scrollIndex.value > 0) {
    scrollIndex.value--
    resetAutoScroll()
  }
}

const startAutoScroll = () => {
  autoScrollTimer.value = setInterval(() => {
    if (scrollIndex.value < totalPages.value - 1) {
      scrollIndex.value++
    } else {
      scrollIndex.value = 0
    }
  }, 3000)
}

const resetAutoScroll = () => {
  if (autoScrollTimer.value) {
    clearInterval(autoScrollTimer.value)
    startAutoScroll()
  }
}

const pauseAutoScroll = () => {
  if (autoScrollTimer.value) {
    clearInterval(autoScrollTimer.value)
    autoScrollTimer.value = null
  }
}

const resumeAutoScroll = () => {
  if (!autoScrollTimer.value) {
    startAutoScroll()
  }
}

// 附近门店
const nearbyStores = ref([
  { id: 9, name: '麦当劳', address: '中山路100号', rating: 4.7, sales: 2345, minPrice: 25, avatar: 'https://images.pexels.com/photos/2983101/pexels-photo-2983101.jpeg?w=60&h=60&fit=crop' },
  { id: 10, name: '瑞幸咖啡', address: '中山路101号', rating: 4.8, sales: 1876, minPrice: 15, avatar: 'https://images.pexels.com/photos/312418/pexels-photo-312418.jpeg?w=60&h=60&fit=crop' },
  { id: 11, name: '张亮麻辣烫', address: '步行街50号', rating: 4.5, sales: 2341, minPrice: 18, avatar: 'https://images.pexels.com/photos/1279330/pexels-photo-1279330.jpeg?w=60&h=60&fit=crop' },
  { id: 12, name: '真功夫', address: '万达广场B1', rating: 4.6, sales: 987, minPrice: 28, avatar: 'https://images.pexels.com/photos/825661/pexels-photo-825661.jpeg?w=60&h=60&fit=crop' },
  { id: 13, name: 'CoCo都可', address: '万象城B1', rating: 4.7, sales: 1567, minPrice: 12, avatar: 'https://images.pexels.com/photos/2392977/pexels-photo-2392977.jpeg?w=60&h=60&fit=crop' },
  { id: 14, name: '探鱼', address: '万达广场3楼', rating: 4.8, sales: 876, minPrice: 60, avatar: 'https://images.pexels.com/photos/2010749/pexels-photo-2010749.jpeg?w=60&h=60&fit=crop' }
])

const allStores = ref([...hotStores.value, ...nearbyStores.value])

const categories = ref([
  { name: '炸鸡汉堡', icon: '🍔', bgColor: '#FFE4B5' },
  { name: '奶茶饮品', icon: '🥤', bgColor: '#FFDBA8' },
  { name: '披萨意面', icon: '🍕', bgColor: '#FFE0B5' },
  { name: '中式快餐', icon: '🍱', bgColor: '#FFD89A' },
  { name: '烧烤烤串', icon: '🍖', bgColor: '#FFD280' },
  { name: '日式料理', icon: '🍣', bgColor: '#FFCC70' },
  { name: '甜品蛋糕', icon: '🍰', bgColor: '#FFE4B5' },
  { name: '川湘小炒', icon: '🌶️', bgColor: '#FFD699' }
])

// 当前店铺详情
const currentStore = reactive({
  id: 0, name: '', rating: 0, sales: 0, minPrice: 0, time: 0, address: '', banner: '', dishes: []
})

// 店铺详情数据
const storeDetails = {
  1: {
    banner: 'https://images.pexels.com/photos/2983101/pexels-photo-2983101.jpeg?w=800&h=200&fit=crop',
    address: '中山路123号',
    dishes: [
      { id: 101, name: '香辣鸡腿堡', description: '香辣多汁的鸡腿肉', price: 18, img: 'https://images.pexels.com/photos/2983101/pexels-photo-2983101.jpeg?w=80&h=80&fit=crop', quantity: 0 },
      { id: 102, name: '薯条', description: '金黄酥脆', price: 10, img: 'https://images.pexels.com/photos/1583884/pexels-photo-1583884.jpeg?w=80&h=80&fit=crop', quantity: 0 },
      { id: 103, name: '可乐', description: '冰爽可口', price: 6, img: 'https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg?w=80&h=80&fit=crop', quantity: 0 }
    ]
  },
  2: {
    banner: 'https://images.pexels.com/photos/2392977/pexels-photo-2392977.jpeg?w=800&h=200&fit=crop',
    address: '中山路125号',
    dishes: [
      { id: 201, name: '多肉葡萄', description: '新鲜葡萄果肉', price: 28, img: 'https://images.pexels.com/photos/2392977/pexels-photo-2392977.jpeg?w=80&h=80&fit=crop', quantity: 0 },
      { id: 202, name: '芝芝莓莓', description: '草莓+奶盖', price: 30, img: 'https://images.pexels.com/photos/2392977/pexels-photo-2392977.jpeg?w=80&h=80&fit=crop', quantity: 0 }
    ]
  }
}

// 更新购物车
const updateCart = (dish) => {
  const cart = JSON.parse(localStorage.getItem('cart') || '[]')
  const existing = cart.find(item => item.id === dish.id)
  
  if (dish.quantity > 0) {
    if (existing) {
      existing.quantity = dish.quantity
    } else {
      cart.push({ id: dish.id, name: dish.name, price: dish.price, quantity: dish.quantity, img: dish.img })
    }
  } else {
    const index = cart.findIndex(item => item.id === dish.id)
    if (index > -1) cart.splice(index, 1)
  }
  
  localStorage.setItem('cart', JSON.stringify(cart))
  window.dispatchEvent(new CustomEvent('cart-updated'))
  if (dish.quantity > 0) ElMessage.success(`已添加 ${dish.name} 到购物车`)
}

const goToStore = (id) => {
  const store = hotStores.value.find(s => s.id === id) || nearbyStores.value.find(s => s.id === id)
  const detail = storeDetails[id]
  
  if (detail && store) {
    currentStore.id = id
    currentStore.name = store.name
    currentStore.rating = store.rating
    currentStore.sales = store.sales
    currentStore.minPrice = store.minPrice
    currentStore.time = store.time
    currentStore.address = detail.address
    currentStore.banner = detail.banner
    
    const cart = JSON.parse(localStorage.getItem('cart') || '[]')
    currentStore.dishes = detail.dishes.map(d => {
      const inCart = cart.find(item => item.id === d.id)
      return { ...d, quantity: inCart ? inCart.quantity : 0 }
    })
    storeDetailVisible.value = true
  } else {
    ElMessage.info(`${store?.name || '该店铺'} 详情开发中`)
  }
}

const closeStoreDetail = () => {
  window.dispatchEvent(new CustomEvent('cart-updated'))
}

const showAllStores = () => { allStoresVisible.value = true }
const filterByCategory = (category) => { ElMessage.info(`查看 ${category} 相关店铺`) }

onMounted(() => {
  startAutoScroll()
  if (scrollContainer.value) {
    scrollContainer.value.addEventListener('mouseenter', pauseAutoScroll)
    scrollContainer.value.addEventListener('mouseleave', resumeAutoScroll)
  }
})

onUnmounted(() => {
  if (autoScrollTimer.value) {
    clearInterval(autoScrollTimer.value)
  }
})
</script>

<style scoped>
.customer-home { min-height: 100%; }

.welcome-card {
  background: linear-gradient(135deg, #FFE4B5 0%, #FFD699 100%);
  border-radius: 20px;
  padding: 30px 40px;
  margin-bottom: 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.welcome-text h2 { font-size: 28px; color: #D2691E; margin-bottom: 8px; }
.stats { display: flex; gap: 40px; }
.stat-number { display: block; font-size: 28px; font-weight: bold; color: #D2691E; }
.stat-label { font-size: 12px; color: #B85C1A; }

.auto-scroll-container { overflow: hidden; }
.scroll-wrapper { display: flex; transition: transform 0.5s ease-in-out; }
.scroll-page { flex-shrink: 0; width: 100%; display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; }
.scroll-controls { display: flex; align-items: center; gap: 12px; }
.scroll-indicator { font-size: 13px; color: #D2691E; min-width: 50px; text-align: center; }

.store-card {
  border: 1px solid #FFE4B5;
  border-radius: 16px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s;
  background: white;
}
.store-card:hover { transform: translateY(-4px); box-shadow: 0 8px 20px rgba(255, 140, 66, 0.15); }
.store-img { height: 130px; background-size: cover; background-position: center; position: relative; }
.rating, .delivery-time { position: absolute; bottom: 8px; background: rgba(0,0,0,0.7); padding: 2px 8px; border-radius: 20px; font-size: 11px; }
.rating { left: 8px; color: #FFD700; }
.delivery-time { right: 8px; color: white; }
.store-info { padding: 12px; }
.store-info h3 { font-size: 14px; margin: 0 0 4px 0; color: #D2691E; }
.min-price { color: #FF6B35 !important; font-weight: bold; }

.category-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(90px, 1fr)); gap: 15px; }
.category-item { text-align: center; cursor: pointer; }
.category-icon { width: 60px; height: 60px; line-height: 60px; font-size: 28px; border-radius: 50%; margin: 0 auto 8px; }

.store-list { display: flex; flex-direction: column; gap: 12px; }
.store-item { display: flex; align-items: center; gap: 15px; padding: 12px; border: 1px solid #FFE4B5; border-radius: 16px; cursor: pointer; }
.store-avatar { width: 60px; height: 60px; background-size: cover; border-radius: 12px; }
.store-detail { flex: 1; }
.store-detail h3 { margin: 0 0 4px 0; color: #D2691E; }
.store-stats { display: flex; gap: 15px; font-size: 11px; color: #666; }

.all-stores-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 15px; }
.store-card-item { display: flex; gap: 10px; padding: 10px; border: 1px solid #eee; border-radius: 12px; cursor: pointer; }
.store-img-small { width: 60px; height: 60px; background-size: cover; border-radius: 8px; }

.store-detail-dialog :deep(.el-dialog__body) { padding: 0; }
.store-banner { height: 180px; background-size: cover; background-position: center; }
.store-info-detail { padding: 16px; border-bottom: 1px solid #eee; }
.store-meta { display: flex; gap: 20px; margin-bottom: 8px; }
.dish-list { padding: 16px; }
.dish-item { display: flex; gap: 15px; padding: 12px 0; border-bottom: 1px solid #f0f0f0; }
.dish-img { width: 70px; height: 70px; background-size: cover; border-radius: 8px; }
.dish-info { flex: 1; }
.dish-price { color: #FF6B35; font-weight: bold; }

.section { background: white; border-radius: 16px; padding: 20px; margin-bottom: 24px; }
.section-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.more { color: #FF8C42; cursor: pointer; }

@media (max-width: 1000px) { .scroll-page { grid-template-columns: repeat(3, 1fr); } }
@media (max-width: 700px) { .scroll-page { grid-template-columns: repeat(2, 1fr); } }
</style>