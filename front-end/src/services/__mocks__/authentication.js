export default {
  authenticate (detail) {
    return new Promise((resolve, reject) => {
      if ((detail.username === 'sunny' || detail.username === 'sunny@taskagile.com') &&
        detail.password === 'JestRocks!') {
        resolve({ result: 'success' })
      } else {
        reject(new Error('Invalid credentials'))
      }
    })
  }
}
