using design_client.Context;
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
    public static class UserAPI
    {
        private static HttpClient client = HttpInstance.GetInstance();

        public static async Task<string> GetToken(string username,string password)
        {
            var request = new FormUrlEncodedContent(new Dictionary<string, string>() {
                {"username",username },
                {"password",password }
            });
            var response = await client.PostAsync("oauth/token", request);
            if (response.IsSuccessStatusCode)
            {
                string str = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<Dictionary<string, string>>(str)["token"];
            }
            else throw new ApiException(await HttpInstance.ExtractError(response));
        }

        public static async Task<User> LoadUser(string? token = null)
        {
            var response = await client.GetAsync($"user/detail?access_token={token?? SecurityContext.Token}");
            return await HttpInstance.GetElseThrow<User>(response);
        }
    }
}
