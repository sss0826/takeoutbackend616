<template>
  <div class="login-container">
    <!-- 左侧装饰区 -->
    <div class="hero-section">
      <div class="hero-content">
        <h1 class="hero-title">餐享</h1>
        <p class="hero-subtitle">网上订餐平台</p>
        <div class="features">
          <div class="feature">
            <div class="feature-icon">🍔</div>
            <div>美食精选</div>
          </div>
          <div class="feature">
            <div class="feature-icon">🚀</div>
            <div>极速配送</div>
          </div>
          <div class="feature">
            <div class="feature-icon">💳</div>
            <div>安全支付</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 右侧登录表单 -->
    <div class="login-section">
      <div class="login-box">
        <h2 class="login-title">欢迎回来</h2>
        <p class="login-subtitle">登录您的账号</p>

        <el-tabs v-model="activeTab" class="login-tabs">
          <el-tab-pane label="账号登录" name="account">
            <el-form>
              <el-form-item>
                <el-input 
                  v-model="accountForm.username" 
                  placeholder="用户名 / 手机号"
                  prefix-icon="User"
                  size="large"
                />
              </el-form-item>
              <el-form-item>
                <el-input 
                  v-model="accountForm.password" 
                  type="password"
                  placeholder="密码"
                  prefix-icon="Lock"
                  size="large"
                  show-password
                />
              </el-form-item>
              <div class="login-options">
                <el-checkbox v-model="remember">记住密码</el-checkbox>
                <span class="forgot-pwd">忘记密码？</span>
              </div>
              <el-form-item label="角色">
                <el-radio-group v-model="userRole">
                  <el-radio :value="0">普通用户</el-radio>
                  <el-radio :value="1">商家</el-radio>
                  <el-radio :value="2">骑手</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-button type="primary" size="large" class="login-btn" @click="handleLogin">
                登 录
              </el-button>
            </el-form>
          </el-tab-pane>

          <el-tab-pane label="验证码登录" name="code">
            <el-form>
              <el-form-item>
                <el-input 
                  v-model="codeForm.phone" 
                  placeholder="手机号"
                  prefix-icon="Phone"
                  size="large"
                />
              </el-form-item>
              <el-form-item>
                <div class="code-row">
                  <el-input 
                    v-model="codeForm.code" 
                    placeholder="验证码"
                    size="large"
                  />
                  <el-button 
                    @click="sendCode" 
                    :disabled="sending"
                    class="code-btn"
                  >
                    {{ codeText }}
                  </el-button>
                </div>
              </el-form-item>
              <el-form-item label="角色">
                <el-radio-group v-model="userRole">
                  <el-radio :value="0">普通用户</el-radio>
                  <el-radio :value="1">商家</el-radio>
                  <el-radio :value="2">骑手</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-button type="primary" size="large" class="login-btn" @click="handleLogin">
                登 录
              </el-button>
            </el-form>
          </el-tab-pane>
        </el-tabs>

        <div class="register-link">
          还没有账号？
          <span @click="goRegister">立即注册</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const activeTab = ref('account')
const remember = ref(false)
const sending = ref(false)
const codeText = ref('获取验证码')
const userRole = ref(0)  // 角色：0普通用户 1商家 2骑手

const accountForm = reactive({ username: '', password: '' })
const codeForm = reactive({ phone: '', code: '' })

const sendCode = () => {
  if (!codeForm.phone) {
    ElMessage.warning('请输入手机号')
    return
  }
  sending.value = true
  let time = 60
  codeText.value = `${time}秒后重试`
  const timer = setInterval(() => {
    time--
    if (time <= 0) {
      clearInterval(timer)
      sending.value = false
      codeText.value = '获取验证码'
    } else {
      codeText.value = `${time}秒后重试`
    }
  }, 1000)
  ElMessage.success('验证码：123456')
}

const handleLogin = () => {
  if (activeTab.value === 'account') {
    if (!accountForm.username || !accountForm.password) {
      ElMessage.error('请输入用户名和密码')
      return
    }
  } else {
    if (!codeForm.phone || !codeForm.code) {
      ElMessage.error('请输入手机号和验证码')
      return
    }
  }
  ElMessage.success('登录成功')
  
  // 根据选择的角色跳转到不同页面
  if (userRole.value === 0) {
    router.push('/customer/home')      // 普通用户首页
  } else if (userRole.value === 1) {
    router.push('/merchant/store')     // 商家门店管理
  } else if (userRole.value === 2) {
    router.push('/rider/delivery')     // 骑手配送列表
  }
}

const goRegister = () => router.push('/register')
</script>

<style scoped>
.login-container {
  display: flex;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.hero-section {
  flex: 1;
  background: linear-gradient(rgba(0,0,0,0.55), rgba(0,0,0,0.55)), url('@/assets/food-bg.jpg');
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.hero-content {
  text-align: center;
  padding: 40px;
}

.hero-title {
  font-size: 56px;
  font-weight: bold;
  margin-bottom: 16px;
  letter-spacing: 4px;
}

.hero-subtitle {
  font-size: 20px;
  opacity: 0.9;
  margin-bottom: 60px;
}

.features {
  display: flex;
  gap: 40px;
  justify-content: center;
}

.feature {
  text-align: center;
}

.feature-icon {
  font-size: 40px;
  margin-bottom: 12px;
}

/* 右侧登录区 */
.login-section {
  width: 480px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: white;
}

.login-box {
  width: 360px;
  padding: 48px 24px;
}

.login-title {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  margin-bottom: 8px;
}

.login-subtitle {
  color: #999;
  margin-bottom: 32px;
}

.login-tabs {
  margin-bottom: 20px;
}

.login-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  font-size: 14px;
}

.forgot-pwd {
  color: #FF6B35;
  cursor: pointer;
}

.forgot-pwd:hover {
  text-decoration: underline;
}

.login-btn {
  width: 100%;
  background: linear-gradient(135deg, #FF8C42 0%, #FF6B35 100%);
  border: none;
  height: 48px;
  font-size: 16px;
  font-weight: bold;
}

.login-btn:hover {
  opacity: 0.9;
}

.code-row {
  display: flex;
  gap: 12px;
}

.code-btn {
  white-space: nowrap;
}

.register-link {
  text-align: center;
  margin-top: 32px;
  color: #666;
  font-size: 14px;
}

.register-link span {
  color: #FF6B35;
  cursor: pointer;
  font-weight: bold;
}

.register-link span:hover {
  text-decoration: underline;
}
</style>