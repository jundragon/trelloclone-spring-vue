export default {
  register (detail) {
    return new Promise((resolve, reject) => {
      if (detail.emailAddress === 'sunny@taskagile.com') {
        resolve({ result: 'success' })
      } else {
        reject(new Error('User already exist'))
      }
    })
  }
}
