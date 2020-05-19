using System;
using System.Collections.Generic;
using System.Text;

namespace design_client.Common
{
    public static class Singleton
    {
        public static T GetInstance<T>(ref T? t, object? loc, Func<T> producer) where T:class
        {
            if(t == null)
            {
                lock (loc ?? typeof(Singleton))
                {
                    if(t == null)
                    {
                        t = producer();
                    }
                }
            }
            return t;
        }
    }
}
