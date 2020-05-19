using System;
using System.Collections.Generic;
using System.Text;

namespace design_client.Model
{
    public class User
    {
        public int Id { get; set; }
        public string Username { get; set; } = "";
        public string Name { get; set; } = "";
        public string Email { get; set; } = "";
        public string Telephone { get; set; } = "";
        public string Description { get; set; } = "";
        public List<string> Roles { get; set; } = new List<string>();
        public List<string> Authorities { get; set; } = new List<string>();
    }
}
