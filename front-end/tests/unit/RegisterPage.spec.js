import { mount, createLocalVue } from '@vue/test-utils'
import RegisterPage from '@/views/RegisterPage'
import VueRouter from 'vue-router'
import Vuelidate from 'vuelidate'
import registrationService from '@/services/registration'
import { i18n } from '@/i18n'

// vm.$router에 접근할 수 있도록 Vue Router 추가
const localVue = createLocalVue()
localVue.use(VueRouter)
localVue.use(Vuelidate)
const router = new VueRouter()

// registrationService 의 mock
jest.mock('@/services/registration')

describe('RegisterPage.vue', () => {
  let wrapper
  let fieldUsername
  let fieldEmailAddress
  let fieldPassword
  let buttonSubmit
  let registerSpy

  beforeEach(() => {
    wrapper = mount(RegisterPage, {
      localVue,
      router,
      i18n,
    })
    fieldUsername = wrapper.find('#username')
    fieldEmailAddress = wrapper.find('#emailAddress')
    fieldPassword = wrapper.find('#password')
    buttonSubmit = wrapper.find('form button[type="submit"]')
    // Create spy for registration service
    registerSpy = jest.spyOn(registrationService, 'register')
  })

  afterEach(() => {
    registerSpy.mockReset()
    registerSpy.mockRestore()
  })

  afterAll(() => {
    jest.restoreAllMocks()
  })

  /**
   * form 요소 검증 (화면 UI)
   */
  it('should render registration form', () => {
    expect(wrapper.find('.logo').attributes().src).toEqual('/images/logo.png')
    expect(wrapper.find('.tagline').text()).toEqual(
      'Open source task management tool'
    )
    expect(fieldUsername.exists()).toBe(true)
    expect(fieldUsername.element.value).toEqual('')
    expect(fieldEmailAddress.element.value).toEqual('')
    expect(fieldPassword.element.value).toEqual('')
    expect(buttonSubmit.text()).toEqual('Create account')
  })

  /**
   * 데이터 모델 초깃값 테스트
   */
  it('should contain data model with initial values', () => {
    expect(wrapper.vm.form.username).toEqual('')
    expect(wrapper.vm.form.emailAddress).toEqual('')
    expect(wrapper.vm.form.password).toEqual('')
  })

  /**
   * 데이터 모델과 폼 입력 필드 간의 바인딩 테스트
   */
  it('should have form inputs bound with data model', () => {
    const username = 'sunny'
    const emailAddress = 'sunny@taskagile.com'
    const password = 'VueJsRocks!'

    fieldUsername.setValue(username)
    fieldEmailAddress.setValue(emailAddress)
    fieldPassword.setValue(password)

    expect(wrapper.vm.form.username).toEqual(username)
    expect(wrapper.vm.form.emailAddress).toEqual(emailAddress)
    expect(wrapper.vm.form.password).toEqual(password)
  })

  /**
   * 폼 이벤트 핸들러의 존재 여부 테스트
   */
  it('should have form submit event handler `submitForm`', () => {
    const stub = jest.fn()
    wrapper.setMethods({ submitForm: stub })
    buttonSubmit.trigger('submit')

    expect(stub).toBeCalled()
  })

  /**
   * 성공적인 회원가입 검증 테스트
   */
  it('should register when it is a new user', async () => {
    expect.assertions(2)

    const stub = jest.fn()
    wrapper.vm.$router.push = stub
    wrapper.vm.form.username = 'sunny'
    wrapper.vm.form.emailAddress = 'sunny@taskagile.com'
    wrapper.vm.form.password = 'JestRocks!'
    await wrapper.vm.submitForm()
    expect(registerSpy).toBeCalled()
    await wrapper.vm.$nextTick()
    expect(stub).toHaveBeenCalledWith({ name: 'login' })
  })

  /**
   * 회원가입 실패 검증 테스트
   */
  it('should fail it is not a new user', async () => {
    expect.assertions(3)

    // mock 에서는 오직 sunny@local 만 새로운 사용자
    wrapper.vm.form.username = 'ted'
    wrapper.vm.form.emailAddress = 'ted@taskagile.com'
    wrapper.vm.form.password = 'JestRocks!'
    expect(wrapper.find('.failed').isVisible()).toBe(false)
    await wrapper.vm.submitForm()
    expect(registerSpy).toBeCalled()
    await wrapper.vm.$nextTick()
    expect(wrapper.find('.failed').isVisible()).toBe(true)
  })

  /**
   * 데이터 검증 테스트
   */
  it('should fail when the email address is invalid', () => {
    const spy = jest.spyOn(registrationService, 'register')
    wrapper.vm.form.emailAddress = 'bad-email-address'
    wrapper.vm.submitForm()
    expect(spy).not.toHaveBeenCalled()
    spy.mockReset()
    spy.mockRestore()
  })

  it('should fail when the username is invalid', () => {
    wrapper.vm.form.username = 'a'
    wrapper.vm.form.emailAddress = 'test@taskagile.com'
    wrapper.vm.form.password = 'JestRocks!'
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })

  it('should fail when the password is invalid', () => {
    wrapper.vm.form.username = 'test'
    wrapper.vm.form.emailAddress = 'test@taskagile.com'
    wrapper.vm.form.password = 'bad!'
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })
})
