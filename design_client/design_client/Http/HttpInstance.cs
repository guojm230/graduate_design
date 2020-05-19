using design_client.Common;
using design_client.Model;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace design_client.Http
{
    /**
     * 与后台交互的HttpClient实例，与其它网站交互时请另外再实例化一个Client
     */
    public static class HttpInstance
    {
        private static HttpClient? client;

        public static HttpClient GetInstance()
        {
            return Singleton.GetInstance(ref client, typeof(HttpInstance),
                () => new HttpClient() {
                    BaseAddress = new Uri("http://localhost:8080/")
                });
        }

        /**
         * 对请求的信息转换为ErrorBody
         */
        public static async Task<ErrorBody> ExtractError(HttpResponseMessage response)
        {
            if (response.IsSuccessStatusCode)
                throw new InvalidOperationException("请求成功，无错误信息");
            var str = await response.Content.ReadAsStringAsync();
            ErrorBody? body = null;
            try
            {
                body = JsonConvert.DeserializeObject<ErrorBody>(str);
            }
            catch (Exception e)
            {
                body = new ErrorBody()
                {
                    Code = (int)response.StatusCode,
                    Msg = (int)response.StatusCode >= 500 ?"服务器异常":"错误请求"
                };
            }
            return body;
        }

        public static async Task<ErrorBody?> SafeExtractError(HttpResponseMessage response)
        {
            try
            {
                return await ExtractError(response);
            }
            catch(Exception e){
                return null;
            }
        }

        public static async void ThrowError(HttpResponseMessage response)
        {
            ErrorBody? body = await SafeExtractError(response);
            if(body != null)
            {
                throw new ApiException(body);
            }
        }

        public static async Task<T> GetElseThrow<T>(HttpResponseMessage response) where T:class
        {
            if (response.IsSuccessStatusCode)
            {
                return JsonConvert.DeserializeObject<T>(await response.Content.ReadAsStringAsync());
            }
            else throw new ApiException(await ExtractError(response));
        }

        public static async Task<T> GetElseThrow<T>(HttpResponseMessage response,
            Func<HttpResponseMessage,T> func)
        {
            if (response.IsSuccessStatusCode)
            {
                return func(response);
            }
            else throw new ApiException(await ExtractError(response));
        }
    }

    public static class HttpHeaders
    {
        public static MediaTypeHeaderValue JSON =
            new MediaTypeHeaderValue("application/json;charset=UTF-8");
    }
}
