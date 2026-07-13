const backendProxyError = () =>
  new Error('TTS backend proxy is not configured')

export default class TTSRecorder {
  connectWebSocket() {
    return backendProxyError()
  }

  webSocketSend() {
    return backendProxyError()
  }

  start() {
    return backendProxyError()
  }

  result() {
    return backendProxyError()
  }
}
