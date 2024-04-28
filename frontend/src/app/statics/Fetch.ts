
export function callFetch<T>(input: RequestInfo | URL, init?: RequestInit):Promise<T> {
  return new Promise<T>((resolve, reject) => {
    fetch(input, init)
      .then(response => {
        if (response.status === 204) {
          return resolve(null as any);
        }
        return response.json()
      })
      .then(data => {
        resolve(data);
      })
      .catch(error => {
        reject(error);
      });
  }
);
}
