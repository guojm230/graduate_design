using design_client.Model;
using System;
using System.Collections.Generic;
using System.Runtime.CompilerServices;
using System.Text;

namespace design_client.Context
{
    public delegate void SecurityHandler<T>(T t);

    public static class SecurityContext
    {
        public static string? Token { get; private set; }
        public static User? User { get; private set; }

        public static event SecurityHandler<User>? Logined = null;
        public static event SecurityHandler<User?>? Logouted = null;

        public static void Login(string token,User user)
        {
            Token = token;
            User = user;
            Logined?.Invoke(user);
        }

        public static bool IsLogin() => !string.IsNullOrEmpty(Token) && User != null;

        public static void Logout()
        {
            Token = null;
            User = null;
            User? user = User;
            Logouted?.Invoke(user);
        }
    }

}
