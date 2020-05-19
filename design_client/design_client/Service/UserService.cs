using design_client.Common;
using design_client.Context;
using design_client.Http;
using design_client.Model;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace design_client.Service
{
    public class UserService
    {
        private static UserService? userService;

        public async Task<User> Login(string username,string password)
        {
            var token = await UserAPI.GetToken(username,password);
            var user = await UserAPI.LoadUser(token);
            SecurityContext.Login(token,user);
            return user;
        }

        public static UserService GetInstance()
        {
            return Singleton.GetInstance(
                ref userService,typeof(UserService),
                () => new UserService());
        }
    }
}
