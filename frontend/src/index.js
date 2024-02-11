const downloadForm = document.getElementById('download-form')

downloadForm.addEventListener('submit', async (event) => {
  event.preventDefault()

  const enquiryId = document.getElementById('enquiryId').value
  const customerId = parseInt(document.getElementById('customerId').value)

  try {
    const response = await fetch(
      `http://localhost:5001/enquiries/${enquiryId}/download/${customerId}`,
    )

    if (!response.ok) {
      throw new Error(`Error downloading file: ${response.statusText}`)
    }

    // Read the response as blob
    const blob = await response.blob()

    // Create a blob URL for the blob object
    const url = window.URL.createObjectURL(blob)

    // Create a link element
    const link = document.createElement('a')
    link.href = url

    // Set the filename based on Content-Disposition header (if available)
    const contentDisposition = response.headers.get('content-disposition')
    let filename = 'downloaded_file.pdf' // default filename
    if (contentDisposition) {
      const filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/
      const matches = contentDisposition.match(filenameRegex)
      if (matches && matches.length > 1) {
        filename = matches[1].replace(/['"]/g, '')
      }
    }
    link.setAttribute('download', filename)

    // Append the link to the document body and trigger the download
    document.body.appendChild(link)
    link.click()

    // Cleanup: remove the link and revoke the blob URL
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)
  } catch (error) {
    console.error('An error occurred:', error.message)
    // Handle error with user notification
  }
})
