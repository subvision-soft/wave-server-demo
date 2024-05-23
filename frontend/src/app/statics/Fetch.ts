


const cache = new Map<string, any>();



export function callFetch<T>(input: RequestInfo | URL, init?: RequestInit,useCache = false):Promise<T> {
  return new Promise<T>((resolve, reject) => {
    if (useCache && cache.has(input.toString())) {
      resolve(cache.get(input.toString()));
    } else {
      fetch(input, init)
        .then(response => {
          if (response.status === 204) {
            return resolve(null as any);
          }
          return response.json()
        })
        .then(data => {
          resolve(data);
          if (useCache) {
            cache.set(input.toString(), data);
          }
        })
        .catch(error => {
          reject(error);
        });
    }

  }
);
}
