import axios from 'axios'
import errorParser from '@/utils/error-parser'

export default {
  /**
   * 새로운 보드 생성하기
   * @param {*} 보드의 세부사항
   */
  create (detail) {
    return new Promise((resolve, reject) => {
      axios.post('/boards', detail).then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  },
  /**
   * Add user to a board
   * @param {*} boardId the id of the board
   * @param {*} usernameOrEmailAddress user's username or email address
   */
  addMember (boardId, usernameOrEmailAddress) {
    return new Promise((resolve, reject) => {
      axios.post('/boards/' + boardId + '/members', { usernameOrEmailAddress }).then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  },
  /**
   * Get a board and everything under it
   * @param {*} boardId the id of the board
   */
  getBoard (boardId) {
    return new Promise((resolve, reject) => {
      axios.get('/boards/' + boardId).then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  }
}
